import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyCarsPageComponent } from './my-cars-page.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormControlModule } from '../../components/form-control/form-control.module';
import { ModalModule } from '../../components/modal/modal.module';
import { ImageUploaderModule } from '../../components/image-uploader/image-uploader.module';
import { PaginatorModule } from '../../components/paginator/paginator.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [MyCarsPageComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    FormControlModule,
    ModalModule,
    ImageUploaderModule,
    PaginatorModule,
    RouterModule,
  ],
})
export class MyCarsPageModule {}
