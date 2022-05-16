import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CachingService {
  private static CACHE: { [key: string]: any } = {};

  constructor() {
    const cacheObjJson = localStorage.getItem('cache_obj');
    if (cacheObjJson) {
      CachingService.CACHE = JSON.parse(cacheObjJson);
      delete CachingService.CACHE['current_location'];
    }
  }

  put(key: string, value: any) {
    CachingService.CACHE[key] = value;
    localStorage.setItem('cache_obj', JSON.stringify(CachingService.CACHE));
  }

  get(key: string): any {
    return CachingService.CACHE[key];
  }
}
