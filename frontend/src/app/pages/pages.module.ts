import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GuestPageModule } from './guest-page/guest-page.module';
import { LoginPageModule } from './login-page/login-page.module';
import { RegisterPageModule } from './register-page/register-page.module';
import { MyCarsPageModule } from './my-cars-page/my-cars-page.module';
import { CarDetailsPageModule } from './car-details-page/car-details-page.module';
import { ExplorePageModule } from './explore-page/explore-page.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ExplorePageModule,
    GuestPageModule,
    LoginPageModule,
    RegisterPageModule,
    MyCarsPageModule,
    CarDetailsPageModule,
  ],
})
export class PagesModule {}
