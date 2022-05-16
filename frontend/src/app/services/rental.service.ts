import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import {
  RentalCreateRequest,
  RentalGetAllRequest,
  RentalResponse,
  RentalsCountRequest,
  RentalsCountResponse,
  RentalsFinancialStatsResponse,
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
    if (!model.page) {
      model.page = 1;
    }

    const params = this.buildParams(model)
      .ofNullable('page')
      .ofNullable('ownerId')
      .ofNullable('renterId')
      .ofNullable('carId')
      .ofNullable('isRentalRequest')
      .ofNullable('rentalId')
      .ofNullable('status')
      .build();

    return this.http.get<Page<RentalResponse[]>>(
      `${environment.API_URL}/rentals`,
      {
        params,
      }
    );
  }

  getCount(model: RentalsCountRequest = {}): Observable<RentalsCountResponse> {
    let params = new HttpParams();
    if (model.ownerId) {
      params = params.append('ownerId', model.ownerId);
    }
    if (model.renterId) {
      params = params.append('renterId', model.renterId);
    }

    return this.http.get<RentalsCountResponse>(
      `${environment.API_URL}/rentals/count`,
      {
        params,
      }
    );
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${environment.API_URL}/rentals/${id}`);
  }

  reject(id: string): Observable<void> {
    return this.http.post<void>(
      `${environment.API_URL}/rentals/${id}/reject`,
      {}
    );
  }

  approve(id: string): Observable<void> {
    return this.http.post<void>(
      `${environment.API_URL}/rentals/${id}/approve`,
      {}
    );
  }

  getRentalDates(carId: string): Observable<string[]> {
    return this.http.get<string[]>(
      `${environment.API_URL}/rentals/rental-dates`,
      { params: new HttpParams().append('carId', carId) }
    );
  }

  getFinancialStats(): Observable<RentalsFinancialStatsResponse> {
    return this.http.get<RentalsFinancialStatsResponse>(
      `${environment.API_URL}/rentals/financial-stats`
    );
  }

  private buildParams(model: any, params: HttpParams = new HttpParams()) {
    return {
      ofNullable: (prop: string) => {
        if (model[prop]) {
          params = params.append(prop, model[prop]);
        }

        return this.buildParams(model, params);
      },
      build: () => {
        return params;
      },
    };
  }
}
