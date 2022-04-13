import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GuestPageComponent } from './pages/guest-page/guest-page.component';
import { GuestPageModule } from './pages/guest-page/guest-page.module';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: GuestPageComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), GuestPageModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
