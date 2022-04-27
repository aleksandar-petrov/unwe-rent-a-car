import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  CarCreateRequest,
  CarResponse,
  CarTransmission,
} from '../../models/car.model';
import { PhotoProgress } from '../../models/photo.model';
import { CarService } from '../../services/car.service';
import { ModalComponent } from '../../components/modal/modal.component';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { switchMap } from 'rxjs';
import { Page } from '../../models/page.model';

@Component({
  selector: 'rac-my-cars-page',
  templateUrl: './my-cars-page.component.html',
  styleUrls: ['./my-cars-page.component.scss'],
})
export class MyCarsPageComponent implements OnInit {
  createCarFormGroup: FormGroup = this.fb.group({
    year: [new Date().getFullYear(), [Validators.required]],
    make: ['Acura', [Validators.required]],
    model: [null, [Validators.required]],
    mileage: [null, [Validators.required, Validators.min(1)]],
    transmission: [CarTransmission.MANUAL, [Validators.required]],
  });

  carsPage: Page<CarResponse[]> | undefined;

  years: number[] = this.generateYears();
  makes: string[] = this.generateMakes();
  isPhotoUploadInProgress: boolean = false;
  photosIds: string[] = [];

  @ViewChild('modal') createCarModal!: ModalComponent;

  constructor(
    private fb: FormBuilder,
    private carService: CarService,
    private userService: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParamMap.subscribe((map) => {
      const page = map.get('page');
      this.fetchCars(page ? +page : 1);
    });
  }

  handleCreate() {
    if (this.createCarFormGroup.invalid || this.isPhotoUploadInProgress) {
      return;
    }

    let form = this.createCarFormGroup.value as CarCreateRequest;

    form = { ...form, photosIds: this.photosIds };

    this.carService.create(form).subscribe((car) => {
      this.createCarModal.close();
      this.router.navigate(['car', 'details', car.id]);
    });
  }

  handleImageChange(photos: PhotoProgress[]) {
    this.isPhotoUploadInProgress = photos.some((p) => p.progress < 100);
    this.photosIds = photos
      .filter((p) => p.progress === 100)
      .map((p) => p.photo.id);
  }

  handleCreateModal() {
    this.createCarModal.open();
  }

  getFirstCarPhoto(car: CarResponse) {
    const photos = car.photos;
    if (!photos || photos.length === 0) {
      return 'assets/images/no-image-car.jpg';
    }

    return car.photos[0].url;
  }

  handlePageChange(page: number) {
    this.fetchCars(page);
    this.router.navigate([], {
      relativeTo: this.activatedRoute,
      queryParams: { page },
      queryParamsHandling: 'merge',
    });
  }

  private generateYears(): number[] {
    const arr = [];
    const currentYear = new Date().getFullYear();

    for (let i = currentYear; i >= 1900; i--) {
      arr.push(i);
    }

    return arr;
  }

  private generateMakes() {
    return [
      'Acura',
      'Alfa Romeo',
      'Aston Martin',
      'Audi',
      'Bentley',
      'BMW',
      'Buick',
      'Cadillac',
      'Can-am',
      'Chevrolet',
      'Chrysler',
      'Dodge',
      'Ferrari',
      'FIAT',
      'Ford',
      'Genesis',
      'GMC',
      'Honda',
      'Hyundai',
      'Infiniti',
      'Jaguar',
      'Jeep',
      'Kia',
      'Lamborghini',
      'Land Rover',
      'Lexus',
      'Lincoln',
      'Lotus',
      'Maserati',
      'Mazda',
      'Mclaren',
      'Mercedes-benz',
      'MINI',
      'Mitsubishi',
      'Nissan',
      'Polaris',
      'Porsche',
      'Ram',
      'Rolls Royce',
      'Smart',
      'Subaru',
      'Tesla',
      'Toyota',
      'Volkswagen',
      'Volvo',
    ];
  }

  private fetchCars(page: number = 1) {
    this.userService.userId$
      .pipe(switchMap((id) => this.carService.getAllByOwnerId(id, page)))
      .subscribe((carsPage) => {
        this.carsPage = carsPage;
      });
  }
}
