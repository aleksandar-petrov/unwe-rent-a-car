import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CarCreateRequest, CarResponse } from '../../models/car.model';
import { map, tap } from 'rxjs/operators';
import { EMPTY, switchMap } from 'rxjs';
import { CarService } from '../../services/car.service';
import { ModalComponent } from '../../components/modal/modal.component';
import { UserService } from '../../services/user.service';
import { ToastrService } from 'ngx-toastr';
import { RentalRequestForm, RentalStatus } from '../../models/rental.model';
import { RentalService } from '../../services/rental.service';
import { faCheck } from '@fortawesome/free-solid-svg-icons';
import { PhotoResponse } from '../../models/photo.model';

@Component({
  selector: 'rac-car-details-page',
  templateUrl: './car-details-page.component.html',
  styleUrls: ['./car-details-page.component.scss'],
})
export class CarDetailsPageComponent implements OnInit {
  car: CarResponse | undefined;
  isViewerOwner: boolean = false;
  firstCarPhoto: string = 'assets/images/no-image-car.jpg';
  carPhotos: PhotoResponse[] = [];
  shouldShowControls: boolean = true;
  pendingVerificationRentalRouterLink: string | undefined;
  invalidRentalDates: string[] = [];
  invalidRentalDatesFetched: boolean = false;

  faCheck = faCheck;

  @ViewChild('editCarModal') editCarModal!: ModalComponent;
  @ViewChild('rentalRequestModal') rentalRequestModal!: ModalComponent;

  constructor(
    private activatedRoute: ActivatedRoute,
    private carService: CarService,
    private userService: UserService,
    private toastrService: ToastrService,
    private rentalService: RentalService,
    private router: Router
  ) {}

  setCar(car: CarResponse): void {
    this.car = car;
    this.setFirstCarPhoto();
    this.setShouldShowControls();
  }

  setShouldShowControls(): void {
    this.shouldShowControls = (this.car?.photos?.length || 0) > 1;
  }

  setFirstCarPhoto(): void {
    const photos = this.car?.photos;
    if (!photos || photos.length === 0) {
      this.firstCarPhoto = 'assets/images/no-image-car.jpg';
      return;
    }

    this.firstCarPhoto = this.car!.photos[0].url;
    this.carPhotos = photos.length > 1 ? this.car!.photos.slice(1) : [];
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .pipe(
        map((paramMap) => {
          return paramMap.get('id');
        }),
        switchMap((id) => {
          if (!id) {
            return EMPTY;
          }

          return this.carService.get(id);
        }),
        tap((car) => {
          this.setCar(car);
        }),
        switchMap((car) => this.rentalService.getRentalDates(car.id)),
        tap((invalidRentalDates) => {
          this.invalidRentalDatesFetched = true;
          this.invalidRentalDates = invalidRentalDates;
        }),
        switchMap(() => this.userService.userId$),
        tap((userId) => {
          this.isViewerOwner = userId === this.car?.owner.id;
        }),
        switchMap((userId) =>
          this.rentalService.getAll({
            carId: this.car?.id,
            renterId: userId,
            isRentalRequest: true,
            status: RentalStatus.PENDING_VERIFICATION,
          })
        )
      )
      .subscribe((rentalsPage) => {
        if (rentalsPage.content.length === 0) {
          return;
        }

        const rental = rentalsPage.content[0];

        this.pendingVerificationRentalRouterLink = `/rentals-panel/renter-panel/rental-requests?selected=${rental.id}`;
      });
  }

  handleEditFormSubmitted(form: CarCreateRequest) {
    this.carService
      .edit(this.car!.id, form)
      .pipe(
        tap(() => this.editCarModal.close()),
        switchMap(() => this.carService.get(this.car!.id))
      )
      .subscribe((car) => {
        this.setCar(car);
        this.toastrService.success(
          'You have successfully modified your car.',
          undefined,
          { positionClass: 'toast-bottom-right' }
        );
      });
  }

  handleEditModalClose() {
    this.car = JSON.parse(JSON.stringify(this.car));
  }

  handleRentalRequestSubmit(form: RentalRequestForm) {
    this.rentalRequestModal.close();
    this.rentalService
      .create({
        ...form,
        carId: this.car!.id,
      })
      .subscribe((rental) => {
        this.router.navigateByUrl(
          `rentals-panel/renter-panel/rental-requests?selected=${rental.id}`
        );
      });
  }

  handlePendingRentalRequest() {
    this.router.navigateByUrl(this.pendingVerificationRentalRouterLink!);
  }
}
