import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CarService } from '../../services/car.service';
import {
  CarResponse,
  CarSearch,
  CarSearchRequest,
} from '../../models/car.model';
import { Page } from '../../models/page.model';
import { UserService } from '../../services/user.service';
import { debounceTime, Observable } from 'rxjs';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Options } from '@angular-slider/ngx-slider';
import {
  faArrowDownShortWide,
  faArrowUpShortWide,
  faFilterCircleXmark,
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'rac-explore-page',
  templateUrl: './explore-page.component.html',
  styleUrls: ['./explore-page.component.scss'],
})
export class ExplorePageComponent implements OnInit {
  carsPage: Page<CarResponse[]> | undefined;
  carSearch: CarSearch | undefined;
  loggedUserId$!: Observable<string>;
  faArrowDownShortWide = faArrowDownShortWide;
  faArrowUpShortWide = faArrowUpShortWide;
  faFilterCircleXmark = faFilterCircleXmark;

  options!: Options;

  carSearchFormGroup: FormGroup = this.fb.group({
    country: [''],
    city: [{ value: '', disabled: true }],
    make: [''],
    model: [{ value: '', disabled: true }],
    minYear: [],
    maxYear: [],
    minMileage: [],
    maxMileage: [],
    minPricePerDay: [],
    maxPricePerDay: [],
    sortBy: [''],
    sortDirection: [{ value: '', disabled: true }],
  });

  cities: string[] = [];
  models: string[] = [];
  showFilter: boolean = false;
  page: number = 1;

  constructor(
    private carService: CarService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParamMap.subscribe((map) => {
      const page = map.get('page');
      this.fetchCars(page ? +page : 1);
    });

    this.carService.getCarSearch().subscribe((carSearch) => {
      this.carSearch = carSearch;
    });

    this.loggedUserId$ = this.userService.userId$;

    this.initCarSearchFormGroupValueChangeHandler();

    this.carSearchFormGroup.valueChanges
      .pipe(debounceTime(500))
      .subscribe(() => {
        this.fetchCars(this.page);
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

  handleClearFilter() {
    this.carSearchFormGroup.patchValue({
      country: '',
      make: '',
      sortBy: '',
      minYear: this.carSearch?.minYear,
      maxYear: this.carSearch?.maxYear,
      minMileage: this.carSearch?.minMileage,
      maxMileage: this.carSearch?.maxMileage,
      minPricePerDay: this.carSearch?.minPricePerDay,
      maxPricePerDay: this.carSearch?.maxPricePerDay,
    });
  }

  private fetchCars(page: number) {
    this.page = page;
    const search = this.carSearchFormGroup.value as CarSearchRequest;
    this.carService.getAll({ page, search }).subscribe((carsPage) => {
      this.carsPage = carsPage;
    });
  }

  private initCarSearchFormGroupValueChangeHandler() {
    this.carSearchFormGroup
      .get('country')
      ?.valueChanges.subscribe((country) => {
        if (country === '') {
          this.cities = [];
          this.carSearchFormGroup.get('city')?.disable();

          return;
        }

        this.carSearchFormGroup.get('city')?.enable();
        this.cities =
          this.carSearch?.countries.find((c) => c.name === country)?.cities ||
          [];
      });

    this.carSearchFormGroup.get('make')?.valueChanges.subscribe((make) => {
      if (make === '') {
        this.models = [];
        this.carSearchFormGroup.get('model')?.disable();

        return;
      }

      this.carSearchFormGroup.get('model')?.enable();
      this.models =
        this.carSearch?.makes.find((m) => m.name === make)?.models || [];
    });

    this.carSearchFormGroup.get('sortBy')?.valueChanges.subscribe((sortBy) => {
      if (sortBy === '') {
        this.carSearchFormGroup.get('sortDirection')?.disable();
        return;
      }

      this.carSearchFormGroup.get('sortDirection')?.enable();
    });
  }
}
