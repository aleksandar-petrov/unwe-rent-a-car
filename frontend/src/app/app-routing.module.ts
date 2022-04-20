import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GuestPageComponent } from './pages/guest-page/guest-page.component';
import { GuestPageModule } from './pages/guest-page/guest-page.module';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { LoggedGuard } from './guards/logged.guard';
import { UserGuard } from './guards/user.guard';
import { MyCarsPageComponent } from './pages/my-cars-page/my-cars-page.component';
import { MyRentalsPageComponent } from './pages/my-rentals-page/my-rentals-page.component';
import { AboutUsPageComponent } from './pages/about-us-page/about-us-page.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: GuestPageComponent,
    canActivate: [LoggedGuard],
  },
  {
    path: 'about-us',
    component: AboutUsPageComponent,
    canActivate: [LoggedGuard],
  },
  {
    path: 'login',
    component: LoginPageComponent,
    canActivate: [LoggedGuard],
  },
  {
    path: 'register',
    component: RegisterPageComponent,
    canActivate: [LoggedGuard],
  },
  {
    path: 'home',
    component: HomePageComponent,
    canActivate: [UserGuard],
  },
  {
    path: 'my-cars',
    component: MyCarsPageComponent,
    canActivate: [UserGuard],
  },
  {
    path: 'my-rentals',
    component: MyRentalsPageComponent,
    canActivate: [UserGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), GuestPageModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
