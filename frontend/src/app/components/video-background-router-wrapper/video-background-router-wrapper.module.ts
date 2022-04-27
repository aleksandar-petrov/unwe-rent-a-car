import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VideoBackgroundRouterWrapperComponent } from './video-background-router-wrapper.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [VideoBackgroundRouterWrapperComponent],
  imports: [CommonModule, RouterModule],
  exports: [VideoBackgroundRouterWrapperComponent],
})
export class VideoBackgroundRouterWrapperModule {}
