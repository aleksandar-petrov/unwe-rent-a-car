import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GuestPageModule } from './guest-page/guest-page.module';
import { LoginPageModule } from './login-page/login-page.module';
import { RegisterPageModule } from './register-page/register-page.module';
import { MyCarsPageModule } from './my-cars-page/my-cars-page.module';
import { CarDetailsPageModule } from './car-details-page/car-details-page.module';
import { ExplorePageModule } from './explore-page/explore-page.module';
import { RentalsPanelPageModule } from './rentals-panel-page/rentals-panel-page.module';
import { AdminPanelPageModule } from './admin-panel-page/admin-panel-page.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ExplorePageModule,
    GuestPageModule,
    LoginPageModule,
    RegisterPageModule,
    AdminPanelPageModule,
    MyCarsPageModule,
    CarDetailsPageModule,
    RentalsPanelPageModule,
  ],
})
export class PagesModule {}
