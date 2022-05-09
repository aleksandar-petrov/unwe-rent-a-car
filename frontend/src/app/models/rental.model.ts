import { UserContactResponse } from './user.model';
import { CarResponse } from './car.model';

export interface RentalRequestForm {
  rentedFrom: string;
  rentedTo: string;
}

export interface RentalCreateRequest {
  rentedFrom: string;
  rentedTo: string;
  carId: string;
}

export interface RentalResponse {
  id: string;
  rentedFrom: string;
  rentedTo: string;
  renter: UserContactResponse;
  status: RentalStatus;
  days: number;
  pricePerDay: number;
  totalPrice: number;
  car: CarResponse;
}

export enum RentalStatus {
  PENDING_VERIFICATION = 'PENDING_VERIFICATION',
  APPROVED = 'APPROVED',
  REJECTED = 'REJECTED',
  STARTED = 'STARTED',
  FINISHED = 'FINISHED',
}

export interface RentalGetAllRequest {
  page?: number;
  renterId?: string;
  ownerId?: string;
  carId?: string;
}
