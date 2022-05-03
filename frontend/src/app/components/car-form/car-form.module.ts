import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarFormComponent } from './car-form.component';
import { FormControlModule } from '../form-control/form-control.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ImageUploaderModule } from '../image-uploader/image-uploader.module';
import { ModalModule } from '../modal/modal.module';
import { GoogleMapsModule } from '../google-maps/google-maps.module';

@NgModule({
  declarations: [CarFormComponent],
  imports: [
    CommonModule,
    FormControlModule,
    FormsModule,
    ReactiveFormsModule,
    ImageUploaderModule,
    ModalModule,
    GoogleMapsModule,
  ],
  exports: [CarFormComponent],
})
export class CarFormModule {}
