import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'rac-form-control',
  templateUrl: './form-control.component.html',
  styleUrls: ['./form-control.component.scss'],
})
export class FormControlComponent implements OnInit {
  @Input() label: string = '';
  @Input() placeholder: string = '';
  @Input() controlName: string = '';
  @Input() type: string = '';
  @Input() formGroup: FormGroup | undefined;
  @Input()
  validationMessage: string = '';

  constructor() {}

  get defaultValidationMessage(): string {
    const value = this.controlName
      .replace(/([a-z])([A-Z])/g, '$1 $2')
      .toLocaleLowerCase();

    return `Please enter a correct ${value}.`;
  }

  ngOnInit(): void {}
}
