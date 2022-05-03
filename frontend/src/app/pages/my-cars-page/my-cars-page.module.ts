import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyCarsPageComponent } from './my-cars-page.component';
import { ModalModule } from '../../components/modal/modal.module';
import { PaginatorModule } from '../../components/paginator/paginator.module';
import { RouterModule } from '@angular/router';
import { CarsGridModule } from '../../components/cars-grid/cars-grid.module';
import { CarFormModule } from '../../components/car-form/car-form.module';
import { GoogleMapsModule } from '../../components/google-maps/google-maps.module';

@NgModule({
  declarations: [MyCarsPageComponent],
  imports: [
    CommonModule,
    ModalModule,
    PaginatorModule,
    RouterModule,
    CarsGridModule,
    CarFormModule,
    GoogleMapsModule,
  ],
})
export class MyCarsPageModule {}
