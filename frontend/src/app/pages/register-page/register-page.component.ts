import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterRequest } from '../../models/register.model';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { ExistingEmailValidator } from '../../services/validators/existing-email.validator';

@Component({
  selector: 'rac-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss'],
})
export class RegisterPageComponent implements OnInit {
  registerFormGroup: FormGroup = this.fb.group({
    firstName: [null, Validators.required],
    lastName: [null, Validators.required],
    email: [
      null,
      [Validators.required, Validators.email],
      [ExistingEmailValidator.createValidator(this.userService)],
    ],
    password: [null, Validators.required],
    confirmPassword: [null, Validators.required],
  });

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {}

  get emailValidationMessage() {
    if (this.registerFormGroup.get('email')?.errors?.['emailAlreadyExists']) {
      return 'This email already exists.';
    }

    return '';
  }

  ngOnInit(): void {}

  handleRegister() {
    if (this.registerFormGroup.invalid) {
      return;
    }

    const registerForm = this.registerFormGroup.value as RegisterRequest;

    this.userService
      .register(registerForm)
      .pipe(switchMap(() => this.userService.login(registerForm, true)))
      .subscribe(() => this.router.navigateByUrl('/explore'));
  }

  asd() {
    console.log(this.registerFormGroup.controls['email']);
  }
}
