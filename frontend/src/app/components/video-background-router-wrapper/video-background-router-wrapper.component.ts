import { Component, OnInit } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'rac-video-background-router-wrapper',
  templateUrl: './video-background-router-wrapper.component.html',
  styleUrls: ['./video-background-router-wrapper.component.scss'],
})
export class VideoBackgroundRouterWrapperComponent implements OnInit {
  shouldBlurVideo: boolean = true;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationStart))
      .subscribe((ev) => {
        const { url } = ev as NavigationStart;

        if (url === '/') {
          this.shouldBlurVideo = false;
          return;
        }

        this.shouldBlurVideo = true;
      });
  }
}
