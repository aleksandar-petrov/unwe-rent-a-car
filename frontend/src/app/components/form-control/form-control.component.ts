import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl } from '@angular/forms';

@Component({
  selector: 'rac-form-control',
  templateUrl: './form-control.component.html',
  styleUrls: ['./form-control.component.scss'],
})
export class FormControlComponent implements OnInit {
  @Input() label: string = '';
  @Input() labelTextClass: string = 'text-gray-700';
  @Input() placeholder: string = '';
  @Input() controlName: string = '';
  @Input() type: string = '';
  @Input() control: AbstractControl | undefined | null;
  @Input()
  validationMessage: string = '';
  @Input() min: number | undefined;
  @Input() typingDisabled: boolean = false;
  @Output() onClick = new EventEmitter<void>();

  constructor() {}

  get defaultValidationMessage(): string {
    const value = this.controlName
      .replace(/([a-z])([A-Z])/g, '$1 $2')
      .toLocaleLowerCase();

    return `Please enter a correct ${value}.`;
  }

  get formControl(): FormControl {
    return this.control as FormControl;
  }

  ngOnInit(): void {}

  shouldShow(type: string): boolean {
    if (!this.control) {
      return false;
    }

    if (type === this.type) {
      return true;
    }

    return type === 'standard' && !['select', 'checkbox'].includes(this.type);
  }

  handleKeyDown(event: KeyboardEvent) {
    if (!this.typingDisabled) {
      return;
    }

    event.preventDefault();
    return false;
  }
}
