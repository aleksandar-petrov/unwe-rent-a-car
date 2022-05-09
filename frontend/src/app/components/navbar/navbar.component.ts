import { Component, HostListener, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Role } from '../../models/user.model';

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
      label: 'Explore',
      link: '/explore',
    },
    {
      label: 'My Cars',
      link: '/my-cars',
    },
    {
      label: 'Rentals Panel',
      link: '/rentals-panel',
    },
  ];

  displayButtons: NavbarButton[] = [];
  logoRoute: string = '/';
  isUserLogged: boolean = false;
  isUserAdmin: boolean = false;
  isScreenMd: boolean = window.innerWidth >= 768;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.user$.subscribe((user) => {
      if (!!user) {
        this.displayButtons = this.loggedDisplayButtons;
        this.logoRoute = '/explore';
        this.isUserAdmin = user.roles.includes(Role.ROLE_ADMIN);
        this.isUserLogged = true;

        return;
      }

      this.displayButtons = this.guestDisplayButtons;
      this.logoRoute = '/';
      this.isUserAdmin = false;
      this.isUserLogged = false;
    });
  }

  handleLogout() {
    this.userService.logout();
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.isScreenMd = window.innerWidth >= 768;
  }
}
