import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'rac-rentals-panel-page',
  templateUrl: './rentals-panel-page.component.html',
  styleUrls: ['./rentals-panel-page.component.scss'],
})
export class RentalsPanelPageComponent implements OnInit {
  panelType!: string;
  rentalType!: string;

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((paramMap) => {
      const panelType = paramMap.get(PanelType.KEY);
      if (!PanelType.TYPES.includes(panelType || '')) {
        this.router.navigateByUrl('/404');
      }

      const rentalType = paramMap.get(RentalType.KEY);
      if (!RentalType.TYPES.includes(rentalType || '')) {
        this.router.navigateByUrl('/404');
      }
    });
  }
}

class PanelType {
  static RENTER_PANEL = 'renter-panel';
  static OWNER_PANEL = 'owner-panel';
  static TYPES = [PanelType.RENTER_PANEL, PanelType.OWNER_PANEL];
  static KEY = 'panel-type';
}

class RentalType {
  static RENTAL_REQUESTS = 'rental-requests';
  static RENTALS = 'rentals';
  static TYPES = [RentalType.RENTAL_REQUESTS, RentalType.RENTALS];
  static KEY = 'rental-type';
}
