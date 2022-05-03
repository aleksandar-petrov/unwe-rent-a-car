import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GuestPageComponent } from './pages/guest-page/guest-page.component';
import { GuestPageModule } from './pages/guest-page/guest-page.module';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { LoggedGuard } from './guards/logged.guard';
import { UserGuard } from './guards/user.guard';
import { MyCarsPageComponent } from './pages/my-cars-page/my-cars-page.component';
import { CarDetailsPageComponent } from './pages/car-details-page/car-details-page.component';
import { ExplorePageComponent } from './pages/explore-page/explore-page.component';
import { RentalsPanelPageComponent } from './pages/rentals-panel-page/rentals-panel-page.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: GuestPageComponent,
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
    path: 'explore',
    component: ExplorePageComponent,
    canActivate: [UserGuard],
  },
  {
    path: 'my-cars',
    component: MyCarsPageComponent,
    canActivate: [UserGuard],
  },
  {
    path: 'rentals-panel/:panel-type/:rental-type',
    component: RentalsPanelPageComponent,
    canActivate: [UserGuard],
  },
  {
    path: 'car/details/:id',
    component: CarDetailsPageComponent,
    canActivate: [UserGuard],
  },
  { path: '404', component: NotFoundPageComponent },
  { path: '**', component: NotFoundPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), GuestPageModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
