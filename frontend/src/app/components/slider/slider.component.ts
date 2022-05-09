import { Component, Input, OnInit } from '@angular/core';
import { LabelType, Options } from '@angular-slider/ngx-slider';
import { AbstractControl } from '@angular/forms';

@Component({
  selector: 'rac-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.scss'],
})
export class SliderComponent implements OnInit {
  @Input() valueControl: AbstractControl | undefined | null;
  @Input() highValueControl: AbstractControl | undefined | null;
  @Input() lowValueLabel: string = '';
  @Input() highValueLabel: string = '';

  @Input() minValue!: number;
  @Input() maxValue!: number;

  options!: Options;
  lowValue: number = 0;
  highValue: number = 0;

  constructor() {}

  ngOnInit(): void {
    this.options = {
      floor: this.minValue || 0,
      ceil: this.maxValue || 10000,
      animate: true,
      animateOnMove: true,
      translate: (value: number, label: LabelType): string => {
        switch (label) {
          case LabelType.Low:
            return this.getLabel(this.lowValueLabel) + value;
          case LabelType.High:
            return this.getLabel(this.highValueLabel) + value;
          default:
            return '' + value;
        }
      },
    };

    this.lowValue = this.minValue;
    this.valueControl?.valueChanges.subscribe((value) => {
      this.lowValue = value;
    });

    this.highValue = this.maxValue;
    this.highValueControl?.valueChanges.subscribe((value) => {
      this.highValue = value;
    });
  }

  handleValueChange(value: number) {
    this.valueControl?.setValue(value);
  }

  handleHighValueChange(highValue: number) {
    this.highValueControl?.setValue(highValue);
  }

  private getLabel(label: string) {
    if (label === '') {
      return '';
    }

    return `<b>${label}:</b>`;
  }
}
