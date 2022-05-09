import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarDetailsPageComponent } from './car-details-page.component';
import { ModalModule } from '../../components/modal/modal.module';
import { ImageViewerDirectiveModule } from '../../directives/image-viewer/image-viewer-directive.module';
import { CarFormModule } from '../../components/car-form/car-form.module';
import { GoogleMapsModule } from '../../components/google-maps/google-maps.module';
import { RentalRequestFormModule } from '../../components/rental-request-form/rental-request-form.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [CarDetailsPageComponent],
  imports: [
    CommonModule,
    ModalModule,
    ImageViewerDirectiveModule,
    CarFormModule,
    GoogleMapsModule,
    RentalRequestFormModule,
    FontAwesomeModule,
  ],
})
export class CarDetailsPageModule {}
