import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ImageViewerDirective } from './image-viewer.directive';

@NgModule({
  declarations: [ImageViewerDirective],
  imports: [CommonModule],
  exports: [ImageViewerDirective],
})
export class ImageViewerDirectiveModule {}
