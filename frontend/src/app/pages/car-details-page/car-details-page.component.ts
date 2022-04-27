import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CarResponse } from '../../models/car.model';
import { map } from 'rxjs/operators';
import { EMPTY, switchMap } from 'rxjs';
import { CarService } from '../../services/car.service';

@Component({
  selector: 'rac-car-details-page',
  templateUrl: './car-details-page.component.html',
  styleUrls: ['./car-details-page.component.scss'],
})
export class CarDetailsPageComponent implements OnInit {
  car: CarResponse | undefined;

  constructor(
    private activatedRoute: ActivatedRoute,
    private carService: CarService
  ) {}

  get firstCarPhoto(): string {
    const photos = this.car?.photos;
    if (!photos || photos.length === 0) {
      return 'assets/images/no-image-car.jpg';
    }

    return this.car!.photos[0].url;
  }

  get shouldShowControls(): boolean {
    return (this.car?.photos?.length || 0) > 1;
  }

  get photoUrls(): string[] {
    return this.car?.photos.map((p) => p.url) || [];
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
        })
      )
      .subscribe((car) => {
        this.car = car;
      });
  }
}
