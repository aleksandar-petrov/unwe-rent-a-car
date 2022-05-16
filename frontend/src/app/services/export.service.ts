import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ExportType } from '../models/export.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ExportService {
  private downloadLinkElement: HTMLAnchorElement = document.createElement('a');

  constructor(private http: HttpClient) {}

  generate(exportType: ExportType) {
    return this.http
      .get<string[]>(`${environment.API_URL}/exports`, {
        params: new HttpParams().append('exportType', exportType),
        responseType: 'blob' as 'json',
      })
      .subscribe((response: any) => {
        const dataType = response.type;
        const binaryData = [];
        binaryData.push(response);
        this.downloadLinkElement.href = window.URL.createObjectURL(
          new Blob(binaryData, { type: dataType })
        );
        this.downloadLinkElement.setAttribute(
          'download',
          `${exportType.toLowerCase()}-${new Date().toISOString()}.json`
        );
        this.downloadLinkElement.click();
      });
  }
}
