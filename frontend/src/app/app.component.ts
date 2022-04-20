import { Component } from '@angular/core';
import { UserService } from './services/user.service';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  constructor(private userService: UserService, private http: HttpClient) {
    userService.autoLogin();
    this.http.get(environment.API_URL + '/users/any').subscribe(console.log);
  }
}
