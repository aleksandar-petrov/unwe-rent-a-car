import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { CarCreateRequest, CarResponse } from '../../models/car.model';
import { CarService } from '../../services/car.service';
import { ModalComponent } from '../../components/modal/modal.component';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { switchMap } from 'rxjs';
import { Page } from '../../models/page.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'rac-my-cars-page',
  templateUrl: './my-cars-page.component.html',
  styleUrls: ['./my-cars-page.component.scss'],
})
export class MyCarsPageComponent implements OnInit {
  carsPage: Page<CarResponse[]> | undefined;

  @ViewChild('modal') createCarModal!: ModalComponent;

  constructor(
    private fb: FormBuilder,
    private carService: CarService,
    private userService: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParamMap.subscribe((map) => {
      const page = map.get('page');
      this.fetchCars(page ? +page : 1);
    });
  }

  handleCreateModal() {
    this.createCarModal.open();
  }

  handleCreateFormSubmitted(form: CarCreateRequest) {
    this.carService.create(form).subscribe((car) => {
      this.createCarModal.close();
      this.router.navigate(['car', 'details', car.id]);
      this.toastrService.success(
        'You have successfully created a car.',
        undefined,
        { positionClass: 'toast-bottom-right' }
      );
    });
  }

  handlePageChange(page: number) {
    this.fetchCars(page);
    this.router.navigate([], {
      relativeTo: this.activatedRoute,
      queryParams: { page },
      queryParamsHandling: 'merge',
    });
  }

  private fetchCars(page: number = 1) {
    this.userService.userId$
      .pipe(switchMap((ownerId) => this.carService.getAll({ ownerId, page })))
      .subscribe((carsPage) => {
        this.carsPage = carsPage;
      });
  }
}
