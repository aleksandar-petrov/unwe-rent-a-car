import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarDetailsPageComponent } from './car-details-page.component';
import { ModalModule } from '../../components/modal/modal.module';
import { ImageViewerDirectiveModule } from '../../directives/image-viewer/image-viewer-directive.module';

@NgModule({
  declarations: [CarDetailsPageComponent],
  imports: [CommonModule, ModalModule, ImageViewerDirectiveModule],
})
export class CarDetailsPageModule {}
