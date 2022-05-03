import { Component, Input, OnInit } from '@angular/core';
import { faEye } from '@fortawesome/free-solid-svg-icons';
import { CarResponse } from '../../models/car.model';

@Component({
  selector: 'rac-cars-grid',
  templateUrl: './cars-grid.component.html',
  styleUrls: ['./cars-grid.component.scss'],
})
export class CarsGridComponent implements OnInit {
  @Input() loggedUserId: string | null | undefined;
  @Input() cars: CarResponse[] = [];
  faEye = faEye;

  constructor() {}

  ngOnInit(): void {}

  getFirstCarPhoto(car: CarResponse) {
    const photos = car.photos;
    if (!photos || photos.length === 0) {
      return 'assets/images/no-image-car.jpg';
    }

    return car.photos[0].url;
  }

  onResized($event: any) {}
}
