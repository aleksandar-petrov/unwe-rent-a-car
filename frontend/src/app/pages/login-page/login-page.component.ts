import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginForm } from '../../models/login.model';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'rac-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent implements OnInit {
  loginFormGroup: FormGroup = this.fb.group({
    email: [null, [Validators.required, Validators.email]],
    password: [null, Validators.required],
    rememberMe: [false],
  });

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private userService: UserService
  ) {}

  ngOnInit(): void {}

  async handleSignIn() {
    if (this.loginFormGroup.invalid) {
      return;
    }

    const loginForm = this.loginFormGroup.value as LoginForm;
    await this.userService.login(loginForm, loginForm.rememberMe);

    this.router.navigateByUrl('/home');

    this.loginFormGroup.reset();
  }
}
