import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterPageComponent } from './register-page.component';
import { RouterModule } from '@angular/router';
import { FormControlModule } from '../../components/form-control/form-control.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [RegisterPageComponent],
  imports: [CommonModule, RouterModule, FormControlModule, ReactiveFormsModule],
})
export class RegisterPageModule {}
