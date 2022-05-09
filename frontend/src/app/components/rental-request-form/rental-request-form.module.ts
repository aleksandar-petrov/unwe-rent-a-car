import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RentalRequestFormComponent } from './rental-request-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';

@NgModule({
  declarations: [RentalRequestFormComponent],
  imports: [CommonModule, ReactiveFormsModule, NgxDaterangepickerMd],
  exports: [RentalRequestFormComponent],
})
export class RentalRequestFormModule {}
