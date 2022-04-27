import { PhotoResponse } from './photo.model';

export interface CarCreateRequest {
  year: number;
  make: string;
  model: string;
  mileage: number;
  transmission: CarTransmission;
  photosIds: string[];
}

export interface CarResponse {
  id: string;
  year: number;
  make: string;
  model: string;
  mileage: number;
  transmission: CarTransmission;
  photos: PhotoResponse[];
  owner: OwnerResponse;
}

export enum CarTransmission {
  MANUAL = 'MANUAL',
  AUTOMATIC = 'AUTOMATIC',
}

export interface OwnerResponse {
  id: string;
  firstName: string;
  lastName: string;
}
