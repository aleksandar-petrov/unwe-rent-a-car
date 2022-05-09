import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import {
  RentalCreateRequest,
  RentalGetAllRequest,
  RentalResponse,
} from '../models/rental.model';
import { Page } from '../models/page.model';

@Injectable({
  providedIn: 'root',
})
export class RentalService {
  constructor(private http: HttpClient) {}

  create(model: RentalCreateRequest): Observable<RentalResponse> {
    return this.http.post<RentalResponse>(
      `${environment.API_URL}/rentals`,
      model
    );
  }

  getAll(model: RentalGetAllRequest = {}): Observable<Page<RentalResponse[]>> {
    let params = new HttpParams().append('page', model.page || 1);
    if (model.ownerId) {
      params = params.append('ownerId', model.ownerId);
    }
    if (model.renterId) {
      params = params.append('renterId', model.renterId);
    }
    if (model.carId) {
      params = params.append('carId', model.carId);
    }

    return this.http.get<Page<RentalResponse[]>>(
      `${environment.API_URL}/rentals`,
      {
        params,
      }
    );
  }
}
