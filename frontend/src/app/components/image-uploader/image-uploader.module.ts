import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ImageUploaderComponent } from './image-uploader.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [ImageUploaderComponent],
  imports: [CommonModule, FontAwesomeModule],
  exports: [ImageUploaderComponent],
})
export class ImageUploaderModule {}
