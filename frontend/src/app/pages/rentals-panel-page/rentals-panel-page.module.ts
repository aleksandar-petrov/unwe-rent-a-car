import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RentalsPanelPageComponent } from './rentals-panel-page.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [RentalsPanelPageComponent],
  imports: [CommonModule, RouterModule],
})
export class RentalsPanelPageModule {}
