import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VideoBackgroundRouterWrapperComponent } from './video-background-router-wrapper.component';
import { RouterModule } from '@angular/router';
import { LoginPageModule } from '../../pages/login-page/login-page.module';
import { RegisterPageModule } from '../../pages/register-page/register-page.module';
import { GuestPageModule } from '../../pages/guest-page/guest-page.module';

@NgModule({
  declarations: [VideoBackgroundRouterWrapperComponent],
  imports: [
    CommonModule,
    RouterModule,
    GuestPageModule,
    LoginPageModule,
    RegisterPageModule,
  ],
  exports: [VideoBackgroundRouterWrapperComponent],
})
export class VideoBackgroundRouterWrapperModule {}
