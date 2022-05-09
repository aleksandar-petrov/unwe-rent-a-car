import { PhotoResponse } from './photo.model';
import { LocationRequest, LocationResponse } from './location.model';
import { UserContactResponse } from './user.model';

export interface CarCreateRequest {
  year: number;
  make: string;
  model: string;
  mileage: number;
  transmission: CarTransmission;
  photosIds: string[];
  pricePerDay: number;
  status: CarStatus;
  location: LocationRequest;
}

export interface CarResponse {
  id: string;
  year: number;
  make: string;
  model: string;
  mileage: number;
  transmission: CarTransmission;
  photos: PhotoResponse[];
  owner: UserContactResponse;
  pricePerDay: number;
  status: CarStatus;
  location: LocationResponse;
}

export enum CarTransmission {
  MANUAL = 'MANUAL',
  AUTOMATIC = 'AUTOMATIC',
}

export enum CarStatus {
  PUBLIC = 'PUBLIC',
  HIDDEN = 'HIDDEN',
}

export interface CarGetAllRequest {
  ownerId?: string;
  page: number;
  search?: CarSearchRequest;
}

export interface CarSearch {
  countries: CountryResponse[];
  makes: MakeResponse[];
  minYear: number;
  maxYear: number;
  minMileage: number;
  maxMileage: number;
  minPricePerDay: number;
  maxPricePerDay: number;
}

export interface CountryResponse {
  name: string;
  cities: string[];
}

export interface MakeResponse {
  name: string;
  models: string[];
}

export interface CarSearchRequest {
  country?: string;
  city?: string;
  make?: string;
  model?: string;
  minYear?: number;
  maxYear?: number;
  minMileage?: number;
  maxMileage?: number;
  minPricePerDay?: number;
  maxPricePerDay?: number;
  sortBy?: string;
  sortDirection?: string;
}
