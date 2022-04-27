import { Injectable } from '@angular/core';
import { CarCreateRequest, CarResponse } from '../models/car.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Page } from '../models/page.model';

@Injectable({
  providedIn: 'root',
})
export class CarService {
  constructor(private http: HttpClient) {}

  create(model: CarCreateRequest): Observable<CarResponse> {
    return this.http.post<CarResponse>(`${environment.API_URL}/cars`, model);
  }

  get(id: string): Observable<CarResponse> {
    return this.http.get<CarResponse>(`${environment.API_URL}/cars/${id}`);
  }

  getAllByOwnerId(
    ownerId: string,
    page: number = 1
  ): Observable<Page<CarResponse[]>> {
    return this.http.get<Page<CarResponse[]>>(`${environment.API_URL}/cars`, {
      params: new HttpParams().append('ownerId', ownerId).append('page', page),
    });
  }
}
