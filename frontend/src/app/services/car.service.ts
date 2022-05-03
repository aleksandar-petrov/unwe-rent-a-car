import { Injectable } from '@angular/core';
import {
  CarCreateRequest,
  CarGetAllRequest,
  CarResponse,
  CarSearch,
  CarSearchRequest,
} from '../models/car.model';
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

  getAll(model: CarGetAllRequest): Observable<Page<CarResponse[]>> {
    let params = new HttpParams().append('page', model.page || 1);
    if (model.ownerId) {
      params = params.append('ownerId', model.ownerId);
    }
    if (model.search) {
      params = this.getCarSearchParams(model.search, params);
    }

    return this.http.get<Page<CarResponse[]>>(`${environment.API_URL}/cars`, {
      params,
    });
  }

  edit(id: string, model: CarCreateRequest): Observable<void> {
    return this.http.put<void>(`${environment.API_URL}/cars/${id}`, model);
  }

  getCarSearch(): Observable<CarSearch> {
    return this.http.get<CarSearch>(`${environment.API_URL}/cars/search`);
  }

  private getCarSearchParams(
    search: CarSearchRequest,
    params: HttpParams
  ): HttpParams {
    const searchKeys = Object.keys(search).filter((k) => {
      return !!(search as any)[k];
    });

    let result = params;
    searchKeys.forEach((searchKey) => {
      result = result.append(searchKey, (search as any)[searchKey]);
    });

    return result;
  }
}
