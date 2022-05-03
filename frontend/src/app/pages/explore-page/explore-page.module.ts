import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExplorePageComponent } from './explore-page.component';
import { CarsGridModule } from '../../components/cars-grid/cars-grid.module';
import { PaginatorModule } from '../../components/paginator/paginator.module';
import { FormControlModule } from '../../components/form-control/form-control.module';
import { ReactiveFormsModule } from '@angular/forms';
import { SliderModule } from '../../components/slider/slider.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [ExplorePageComponent],
  imports: [
    CommonModule,
    CarsGridModule,
    PaginatorModule,
    FormControlModule,
    ReactiveFormsModule,
    SliderModule,
    FontAwesomeModule,
  ],
})
export class ExplorePageModule {}
