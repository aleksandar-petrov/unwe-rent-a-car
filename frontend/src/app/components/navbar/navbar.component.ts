import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';

interface NavbarButton {
  label: string;
  link: string;
}

@Component({
  selector: 'rac-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  guestDisplayButtons: NavbarButton[] = [
    {
      label: 'About us',
      link: '/about-us',
    },
    {
      label: 'Login',
      link: '/login',
    },
    {
      label: 'Register',
      link: '/register',
    },
  ];

  loggedDisplayButtons: NavbarButton[] = [
    {
      label: 'Home',
      link: '/home',
    },
    {
      label: 'My Cars',
      link: '/my-cars',
    },
    {
      label: 'My Rentals',
      link: '/my-rentals',
    },
  ];

  displayButtons: NavbarButton[] = [];
  logoRoute: string = '/';
  isUserLogged: boolean = false;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.user$.subscribe((user) => {
      if (!!user) {
        this.displayButtons = this.loggedDisplayButtons;
        this.logoRoute = '/home';
        this.isUserLogged = true;

        return;
      }

      this.displayButtons = this.guestDisplayButtons;
      this.logoRoute = '/';
      this.isUserLogged = false;
    });
  }

  handleLogout() {
    this.userService.logout();
  }
}
