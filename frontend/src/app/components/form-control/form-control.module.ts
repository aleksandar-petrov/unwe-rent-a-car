import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControlComponent } from './form-control.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [FormControlComponent],
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  exports: [FormControlComponent],
})
export class FormControlModule {}
