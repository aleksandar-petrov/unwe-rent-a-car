import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterForm } from '../../models/register.model';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'rac-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss'],
})
export class RegisterPageComponent implements OnInit {
  registerFormGroup: FormGroup = this.fb.group({
    firstName: [null, Validators.required],
    lastName: [null, Validators.required],
    email: [null, [Validators.required, Validators.email]],
    password: [null, Validators.required],
    confirmPassword: [null, Validators.required],
  });

  constructor(private fb: FormBuilder, private userService: UserService) {}

  ngOnInit(): void {}

  handleRegister() {
    if (this.registerFormGroup.invalid) {
      return;
    }

    const registerForm = this.registerFormGroup.value as RegisterForm;

    this.userService.register(registerForm).subscribe(console.log);
  }
}
