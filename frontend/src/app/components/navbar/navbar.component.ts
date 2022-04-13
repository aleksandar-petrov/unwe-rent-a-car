import { Component, OnInit } from '@angular/core';

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
  displayButtons: NavbarButton[] = [
    {
      label: 'Login',
      link: '/login',
    },
    {
      label: 'Register',
      link: '/register',
    },
  ];

  constructor() {}

  ngOnInit(): void {}
}
