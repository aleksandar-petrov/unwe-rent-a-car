import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  CarCreateRequest,
  CarResponse,
  CarStatus,
  CarTransmission,
} from '../../models/car.model';
import { PhotoProgress, PhotoResponse } from '../../models/photo.model';
import { LocationResponse } from '../../models/location.model';

@Component({
  selector: 'rac-car-form',
  templateUrl: './car-form.component.html',
  styleUrls: ['./car-form.component.scss'],
})
export class CarFormComponent implements OnInit, OnChanges {
  @Input() car: CarResponse | undefined;
  @Input() submitLabel: string = 'Submit';
  @Output() onSubmit = new EventEmitter<CarCreateRequest>();

  carFormGroup!: FormGroup;

  years: number[] = this.generateYears();
  makes: string[] = this.generateMakes();
  isPhotoUploadInProgress: boolean = false;
  photosIds: string[] = [];

  constructor(private fb: FormBuilder) {}

  get photos(): PhotoResponse[] {
    return this.car?.photos || [];
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.car) {
      this.initForm();
    }
  }

  ngOnInit(): void {
    this.initForm();
  }

  handleImageChange(photos: PhotoProgress[]) {
    this.isPhotoUploadInProgress = photos.some((p) => p.progress < 100);
    this.photosIds = photos
      .filter((p) => p.progress === 100)
      .map((p) => p.photo.id);
  }

  handleSubmit() {
    if (this.carFormGroup.invalid || this.isPhotoUploadInProgress) {
      return;
    }

    let form = this.carFormGroup.value as CarCreateRequest;

    form = { ...form, photosIds: this.photosIds };

    this.onSubmit.emit(form);
  }

  handleLocationSubmit(location: LocationResponse) {
    this.carFormGroup.get('location')?.patchValue(location);
  }

  private initForm() {
    this.carFormGroup = this.fb.group({
      year: [this.car?.year || new Date().getFullYear(), [Validators.required]],
      make: [this.car?.make || 'Acura', [Validators.required]],
      model: [this.car?.model || null, [Validators.required]],
      mileage: [
        this.car?.mileage || null,
        [Validators.required, Validators.min(1)],
      ],
      transmission: [
        this.car?.transmission || CarTransmission.MANUAL,
        [Validators.required],
      ],
      pricePerDay: [
        this.car?.pricePerDay || null,
        [Validators.required, Validators.min(1)],
      ],
      status: [this.car?.status || CarStatus.PUBLIC, [Validators.required]],
      location: this.fb.group({
        latitude: [this.car?.location?.latitude || null, [Validators.required]],
        longitude: [
          this.car?.location?.longitude || null,
          [Validators.required],
        ],
        address: [this.car?.location?.address || null, [Validators.required]],
        city: [this.car?.location?.city || null, [Validators.required]],
        country: [this.car?.location?.country || null, [Validators.required]],
      }),
    });
  }

  private generateYears(): number[] {
    const arr = [];
    const currentYear = new Date().getFullYear();

    for (let i = currentYear; i >= 1900; i--) {
      arr.push(i);
    }

    return arr;
  }

  private generateMakes() {
    return [
      'Acura',
      'Alfa Romeo',
      'Aston Martin',
      'Audi',
      'Bentley',
      'BMW',
      'Buick',
      'Cadillac',
      'Can-am',
      'Chevrolet',
      'Chrysler',
      'Dodge',
      'Ferrari',
      'FIAT',
      'Ford',
      'Genesis',
      'GMC',
      'Honda',
      'Hyundai',
      'Infiniti',
      'Jaguar',
      'Jeep',
      'Kia',
      'Lamborghini',
      'Land Rover',
      'Lexus',
      'Lincoln',
      'Lotus',
      'Maserati',
      'Mazda',
      'Mclaren',
      'Mercedes-benz',
      'MINI',
      'Mitsubishi',
      'Nissan',
      'Polaris',
      'Porsche',
      'Ram',
      'Rolls Royce',
      'Smart',
      'Subaru',
      'Tesla',
      'Toyota',
      'Volkswagen',
      'Volvo',
    ];
  }
}
