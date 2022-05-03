import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarsGridComponent } from './cars-grid.component';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ImageViewerDirectiveModule } from '../../directives/image-viewer/image-viewer-directive.module';

@NgModule({
  declarations: [CarsGridComponent],
  imports: [
    CommonModule,
    RouterModule,
    FontAwesomeModule,
    ImageViewerDirectiveModule,
  ],
  exports: [CarsGridComponent],
})
export class CarsGridModule {}
