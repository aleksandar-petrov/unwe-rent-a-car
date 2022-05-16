import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment/moment';
import { Moment } from 'moment/moment';
import { RentalRequestForm } from '../../models/rental.model';
import { DaterangepickerComponent } from 'ngx-daterangepicker-material';

@Component({
  selector: 'rac-rental-request-form',
  templateUrl: './rental-request-form.component.html',
  styleUrls: ['./rental-request-form.component.scss'],
})
export class RentalRequestFormComponent implements OnInit {
  @Input() pricePerDay: number = 0;
  @Input() invalidDates: string[] = [];
  @Output() onSubmit = new EventEmitter<RentalRequestForm>();

  @ViewChild('dateRangePicker') dateRangePicker!: DaterangepickerComponent;

  rentalRequestForm: FormGroup = this.fb.group({
    rentedFrom: [null, Validators.required],
    rentedTo: [null, Validators.required],
  });
  minDate = moment(new Date());
  daysDiff: number = 0;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {}

  handleSubmit() {
    if (this.rentalRequestForm.invalid) {
      return;
    }

    this.onSubmit.emit(this.rentalRequestForm.value as RentalRequestForm);
  }

  isInvalidDate = (date: Moment) => {
    return this.invalidDates.includes(date.format('yyyy-MM-DD'));
  };

  handleDatesUpdated(event: any) {
    const { startDate, endDate } = event as {
      startDate: Moment;
      endDate: Moment;
    };

    this.rentalRequestForm
      .get('rentedFrom')
      ?.setValue(startDate.format('yyyy-MM-DD'));
    this.rentalRequestForm
      .get('rentedTo')
      ?.setValue(endDate.format('yyyy-MM-DD'));

    this.daysDiff = endDate.diff(startDate, 'days') + 1;
  }
}
