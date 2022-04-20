import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GuestPageComponent } from './guest-page.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [GuestPageComponent],
  imports: [CommonModule, RouterModule],
})
export class GuestPageModule {}
