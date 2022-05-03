import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterRequest } from '../../models/register.model';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { RacValidators } from '../../services/validators/validators';

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
      [RacValidators.existingEmail(this.userService)],
    ],
    password: [null, Validators.required],
    confirmPassword: [null, Validators.required],
    phoneNumber: [
      null,
      [Validators.required, Validators.pattern(/^(([+]?359)|0)8[789]\d{7}$/)],
      [RacValidators.existingPhoneNumber(this.userService)],
    ],
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

  get phoneNumberValidationMessage() {
    if (
      this.registerFormGroup.get('phoneNumber')?.errors?.[
        'phoneNumberAlreadyExists'
      ]
    ) {
      return 'This phone number already exists.';
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
}
