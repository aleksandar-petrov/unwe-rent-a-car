import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GoogleMapsComponent } from './google-maps.component';
import { AgmCoreModule } from '@agm/core';

@NgModule({
  declarations: [GoogleMapsComponent],
  imports: [CommonModule, AgmCoreModule],
  exports: [GoogleMapsComponent],
})
export class GoogleMapsModule {}
