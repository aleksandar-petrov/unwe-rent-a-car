import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  NgZone,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { MapsAPILoader } from '@agm/core';
import { LocationResponse } from '../../models/location.model';
import { CachingService } from '../../services/caching.service';
import Geocoder = google.maps.Geocoder;

@Component({
  selector: 'rac-google-maps',
  templateUrl: './google-maps.component.html',
  styleUrls: ['./google-maps.component.scss'],
})
export class GoogleMapsComponent implements OnInit {
  @Input() static: boolean = false;
  @Input() zoom: number = 15;
  @Output() onSubmit = new EventEmitter<LocationResponse>();
  @ViewChild('search') searchElementRef!: ElementRef;
  private geoCoder!: Geocoder;

  constructor(
    private mapsAPILoader: MapsAPILoader,
    private ngZone: NgZone,
    private cachingService: CachingService
  ) {}

  _location!: LocationResponse;

  get location(): LocationResponse {
    return this._location;
  }

  @Input() set location(location: LocationResponse | undefined) {
    this._location = location || {
      latitude: 42.69644123384162,
      longitude: 23.3195688006836,
      address: '',
      city: '',
      country: '',
    };
  }

  ngOnInit(): void {}

  initMapsAPI() {
    this.mapsAPILoader.load().then(() => {
      if (this.location.address === '') {
        this.setCurrentLocation();
      }
      this.geoCoder = new google.maps.Geocoder();

      const autocomplete = new google.maps.places.Autocomplete(
        this.searchElementRef.nativeElement
      );

      autocomplete.addListener('place_changed', () => {
        this.ngZone.run(() => {
          const place: google.maps.places.PlaceResult = autocomplete.getPlace();

          if (place.geometry === undefined || place.geometry === null) {
            return;
          }

          this.location = {
            ...this.location,
            latitude: place.geometry.location.lat(),
            longitude: place.geometry.location.lng(),
          };

          this.fetchAddress(this.location.latitude, this.location.longitude);
        });
      });
    });
  }

  setCurrentLocation() {
    const cachedCurrentLocation = this.cachingService.get('current_location');

    if (cachedCurrentLocation) {
      this.location = {
        ...this.location,
        latitude: cachedCurrentLocation.currentLatitude,
        longitude: cachedCurrentLocation.currentLongitude,
      };

      this.fetchAddress(this.location.latitude, this.location.longitude);

      return;
    }

    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.location = {
          ...this.location,
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        };

        const currentLocation = {
          currentLatitude: this.location.latitude,
          currentLongitude: this.location.longitude,
        };

        this.cachingService.put('current_location', currentLocation);

        this.fetchAddress(this.location.latitude, this.location.longitude);
      });
    }
  }

  handleMarkerDragEnd(event: google.maps.MouseEvent) {
    this.location = {
      ...this.location,
      latitude: event.latLng.lat(),
      longitude: event.latLng.lng(),
    };
    this.fetchAddress(this.location.latitude, this.location.longitude);
  }

  handleSubmit() {
    if (
      !this.location.address ||
      !this.location.city ||
      !this.location.address
    ) {
      return;
    }

    this.onSubmit.emit(this.location);
  }

  handleMarkerClick() {
    if (!this.static) {
      return;
    }

    window.open(
      `http://www.google.com/maps/place/${this.location.latitude},${this.location.longitude}`,
      '_blank'
    );
  }

  handleMapReady(map: google.maps.Map) {
    if (!this.static) {
      map.addListener('click', (event: google.maps.MouseEvent) => {
        this.ngZone.run(() => {
          this.location = {
            ...this.location,
            latitude: event.latLng.lat(),
            longitude: event.latLng.lng(),
          };

          this.fetchAddress(this.location.latitude, this.location.longitude);
        });
      });
    }
  }

  private fetchAddress(latitude: number, longitude: number) {
    const cacheKey = btoa(JSON.stringify({ la: latitude, lo: longitude }));
    const cachedLocation = this.cachingService.get(cacheKey);

    if (cachedLocation) {
      this.location = { ...cachedLocation };
      return;
    }

    this.geoCoder.geocode(
      {
        location: { lat: latitude, lng: longitude },
      },
      (results, status) => {
        if (status === 'OK') {
          if (results[0]) {
            this.location = {
              latitude,
              longitude,
              address: results[0].formatted_address,
              city: results[0].address_components.filter((a) =>
                a.types.includes('locality')
              )[0].long_name,
              country: results[0].address_components.filter((a) =>
                a.types.includes('country')
              )[0].long_name,
            };

            this.cachingService.put(cacheKey, { ...this.location });
          } else {
            window.alert('No results found');
          }
        } else {
          window.alert('Geocoder failed due to: ' + status);
        }
      }
    );
  }
}
