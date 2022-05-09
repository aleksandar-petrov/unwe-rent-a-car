import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarModule } from './components/navbar/navbar.module';
import { VideoBackgroundRouterWrapperModule } from './components/video-background-router-wrapper/video-background-router-wrapper.module';
import { JwtModule } from '@auth0/angular-jwt';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { UserService } from './services/user.service';
import { NgxSmartModalModule } from 'ngx-smart-modal';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { PagesModule } from './pages/pages.module';
import { AgmCoreModule } from '@agm/core';
import { NgxSliderModule } from '@angular-slider/ngx-slider';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NavbarModule,
    PagesModule,
    VideoBackgroundRouterWrapperModule,
    HttpClientModule,
    BrowserAnimationsModule,
    NgxSmartModalModule.forRoot(),
    ToastrModule.forRoot({ positionClass: 'toast-top-center' }),
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return UserService.TOKEN;
        },
      },
    }),
    FontAwesomeModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyC0Knyo9t3mD6ryeNJn46WVV8P4tvSi_nU',
      libraries: ['places'],
    }),
    NgxSliderModule,
    NgxDaterangepickerMd.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
