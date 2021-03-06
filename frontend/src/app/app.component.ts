import { Component } from '@angular/core';
import { UserService } from './services/user.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  isAppInitialized$ = this.userService.anyUserExists$;

  constructor(private userService: UserService, private http: HttpClient) {
    userService.initialize();
  }
}
