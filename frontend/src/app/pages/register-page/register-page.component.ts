import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterRequest } from '../../models/register.model';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { RacValidators } from '../../services/validators/validators';
import { map } from 'rxjs/operators';

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
    confirmPassword: [
      null,
      [Validators.required, RacValidators.mustMatch('password')],
    ],
    phoneNumber: [
      null,
      [Validators.required, Validators.pattern(/^(([+]359)|0)8[789]\d{7}$/)],
      [RacValidators.existingPhoneNumber(this.userService)],
    ],
  });

  isFirstRegistration$ = this.userService.anyUserExists$.pipe(map((x) => !x));

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

  get confirmPasswordValidationMessage() {
    if (this.registerFormGroup.get('confirmPassword')?.errors?.['mustMatch']) {
      return 'Password and confirm password must match.';
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

  ngOnInit(): void {
    this.registerFormGroup.get('password')?.valueChanges.subscribe(() => {
      const confirmPassword = this.registerFormGroup.get('confirmPassword');
      confirmPassword?.setValue(confirmPassword?.value);
    });
  }

  handleRegister() {
    if (this.registerFormGroup.invalid) {
      return;
    }

    const registerForm = this.registerFormGroup.value as RegisterRequest;

    this.userService
      .register(registerForm)
      .pipe(switchMap(() => this.userService.login(registerForm, true)))
      .subscribe(() => this.router.navigateByUrl('/my-cars'));
  }
}
