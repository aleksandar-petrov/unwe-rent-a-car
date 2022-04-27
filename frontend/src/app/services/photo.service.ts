import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpEventType,
  HttpParams,
  HttpProgressEvent,
} from '@angular/common/http';
import {
  PhotoPatchRequest,
  PhotoProgress,
  PhotoResponse,
  PhotoType,
  PhotoUploadResponse,
} from '../models/photo.model';
import { filter, lastValueFrom, Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class PhotoService {
  constructor(private http: HttpClient) {}

  async uploadImage(file: File): Promise<Observable<PhotoProgress>> {
    const type =
      PhotoType[
        file.type.replace('image/', '').toUpperCase() as keyof typeof PhotoType
      ];

    const photoUploadResponse = await lastValueFrom(
      this.generatePresignedUploadUrl(type)
    );

    const photo = {
      id: photoUploadResponse.id,
      url: photoUploadResponse.url,
    } as PhotoResponse;

    const base64 = await this.toBase64(file);

    return this.http
      .put(photoUploadResponse.presignedUploadUrl, file, {
        reportProgress: true,
        observe: 'events',
      })
      .pipe(
        filter((event) => event.type === HttpEventType.UploadProgress),
        map((event) => {
          const progressEvent = event as HttpProgressEvent;
          const progress = Math.floor(
            (progressEvent.loaded /
              (progressEvent.total || progressEvent.loaded)) *
              100
          );

          return {
            progress,
            photo:
              progress === 100
                ? { ...photo, base64 }
                : { id: photo.id, base64 },
          } as PhotoProgress;
        }),
        tap((progress) => {
          if (progress.progress === 100) {
            this.patch(photo.id, { isUploaded: true }).subscribe();
          }
        })
      );
  }

  private toBase64(file: File): Promise<any> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = (error) => reject(error);
    });
  }

  private patch(id: string, model: PhotoPatchRequest): Observable<void> {
    return this.http.patch<void>(`${environment.API_URL}/photos/${id}`, model);
  }

  private generatePresignedUploadUrl(
    type: PhotoType
  ): Observable<PhotoUploadResponse> {
    return this.http.post<PhotoUploadResponse>(
      `${environment.API_URL}/photos`,
      null,
      { params: new HttpParams().append('type', type) }
    );
  }
}
