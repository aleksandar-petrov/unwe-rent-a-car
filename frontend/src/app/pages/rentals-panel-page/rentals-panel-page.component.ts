import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RentalService } from '../../services/rental.service';
import { switchMap, tap } from 'rxjs';
import { UserService } from '../../services/user.service';
import { Page } from '../../models/page.model';
import {
  RentalResponse,
  RentalsCountResponse,
  RentalStatus,
} from '../../models/rental.model';
import { Action } from '../../components/table/table.component';
import {
  faCheck,
  faEye,
  faRemove,
  faTrash,
} from '@fortawesome/free-solid-svg-icons';
import { DatePipe } from '@angular/common';
import { ModalComponent } from '../../components/modal/modal.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'rac-rentals-panel-page',
  templateUrl: './rentals-panel-page.component.html',
  styleUrls: ['./rentals-panel-page.component.scss'],
})
export class RentalsPanelPageComponent implements OnInit {
  readonly RENTALS_PANEL = '/rentals-panel';

  panelType!: string;
  rentalType!: string;
  loggedUserId!: string;
  PanelType = PanelType;
  RentalType = RentalType;
  tableHeaders: string[] = [];
  tableRows: string[][] = [];
  rentalsPage!: Page<RentalResponse[]>;
  rentalsCount: RentalsCountResponse | undefined;
  tableActions: Action[][] = [];
  selectedRentalId: string | undefined;
  dialogText: string = '';

  @ViewChild('dialogModal') dialogModal!: ModalComponent;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private rentalService: RentalService,
    private userService: UserService,
    private datePipe: DatePipe,
    private toastrService: ToastrService
  ) {}

  dialogHandler: () => void = () => {};

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .pipe(
        tap((paramMap) => {
          const panelType = paramMap.get(PanelType.KEY);
          if (!PanelType.TYPES.includes(panelType || '')) {
            this.router.navigate([
              this.RENTALS_PANEL,
              PanelType.RENTER_PANEL,
              RentalType.RENTAL_REQUESTS,
            ]);

            return;
          }

          this.panelType = panelType!;

          const rentalType = paramMap.get(RentalType.KEY);
          if (!RentalType.TYPES.includes(rentalType || '')) {
            this.router.navigate([
              this.RENTALS_PANEL,
              this.panelType,
              RentalType.RENTAL_REQUESTS,
            ]);

            return;
          }

          this.rentalType = rentalType!;
        }),
        switchMap(() => this.userService.userId$),
        tap((userId) => {
          this.loggedUserId = userId;
        }),
        switchMap(() => this.activatedRoute.queryParamMap),
        tap((queryParamMap) => {
          this.selectedRentalId = queryParamMap.get('selected') || undefined;
        })
      )
      .subscribe(() => {
        this.fetchRentals();
      });
  }

  private fetchRentals() {
    const rentalsSearch = {
      renterId:
        this.panelType === PanelType.RENTER_PANEL
          ? this.loggedUserId
          : undefined,
      ownerId:
        this.panelType === PanelType.OWNER_PANEL
          ? this.loggedUserId
          : undefined,
    };

    this.rentalService
      .getAll({
        ...rentalsSearch,
        isRentalRequest: this.rentalType === RentalType.RENTAL_REQUESTS,
        rentalId: this.selectedRentalId,
      })
      .subscribe((rentalsPage) => {
        this.rentalsPage = rentalsPage;
        this.generateTable();
      });

    this.rentalService.getCount(rentalsSearch).subscribe((rentalsCount) => {
      this.rentalsCount = rentalsCount;
    });
  }

  private generateTable() {
    this.tableHeaders = [
      'Car',
      'Period',
      'Days',
      'Price per day',
      'Total Price',
      'Status',
      this.panelType === PanelType.RENTER_PANEL ? 'Owner' : 'Renter',
    ];

    this.tableRows = this.rentalsPage.content
      .map((r) => {
        return [
          `${r.car.make} ${r.car.model} ${r.car.year}`,
          `${this.datePipe.transform(
            r.rentedFrom,
            'mediumDate'
          )} - ${this.datePipe.transform(r.rentedTo, 'mediumDate')}`,
          r.days.toString(),
          `${r.pricePerDay} $`,
          `${r.totalPrice} $`,
          r.status,
          this.panelType === PanelType.RENTER_PANEL
            ? `${r.car.owner.firstName} ${r.car.owner.lastName}, ${r.car.owner.phoneNumber}`
            : `${r.renter.firstName} ${r.renter.lastName}, ${r.renter.phoneNumber}`,
        ];
      })
      .reduce((rows, row) => rows.concat([row]), [] as string[][]);

    this.tableActions = this.rentalsPage.content.map((r) => {
      const defaultActions = [
        {
          icon: faEye,
          handler: () => {
            this.router.navigate(['/car', 'details', r.car.id]);
          },
          class: 'bg-blue-600 hover:bg-blue-700 active:bg-blue-800',
        },
      ] as Action[];

      if (this.panelType === PanelType.RENTER_PANEL) {
        if (r.status === RentalStatus.PENDING_VERIFICATION) {
          return [
            ...defaultActions,
            {
              icon: faTrash,
              handler: () => {
                this.dialogText =
                  'Are you sure you want to delete your rental request?';
                this.dialogHandler = () => {
                  this.rentalService.delete(r.id).subscribe(() => {
                    this.fetchRentals();
                    this.dialogModal.close();
                    this.toastrService.success(
                      `You have successfully deleted your rental request.`,
                      undefined,
                      { positionClass: 'toast-bottom-right' }
                    );
                  });
                };
                this.dialogModal.open();
              },
              class: 'bg-rose-700 hover:bg-rose-800 active:bg-rose-900',
            },
          ];
        }

        return defaultActions;
      }

      if (r.status !== RentalStatus.PENDING_VERIFICATION) {
        return defaultActions;
      }

      return defaultActions.concat(
        {
          icon: faCheck,
          handler: () => {
            this.dialogText = `Are you sure you want to approve ${r.renter.firstName} ${r.renter.lastName}'s rental request?`;
            this.dialogHandler = () => {
              this.rentalService.approve(r.id).subscribe(() => {
                this.fetchRentals();
                this.dialogModal.close();
                this.toastrService.success(
                  `You have successfully approved ${r.renter.firstName} ${r.renter.lastName}'s rental request.`,
                  undefined,
                  { positionClass: 'toast-bottom-right' }
                );
              });
            };
            this.dialogModal.open();
          },
          class: 'bg-green-500 hover:bg-green-600 active:bg-green-700',
        },
        {
          icon: faRemove,
          handler: () => {
            this.dialogText = `Are you sure you want to reject ${r.renter.firstName} ${r.renter.lastName}'s request?`;
            this.dialogHandler = () => {
              this.rentalService.reject(r.id).subscribe(() => {
                this.fetchRentals();
                this.dialogModal.close();
                this.toastrService.success(
                  `You have successfully rejected ${r.renter.firstName} ${r.renter.lastName}'s request.`,
                  undefined,
                  { positionClass: 'toast-bottom-right' }
                );
              });
            };
            this.dialogModal.open();
          },
          class: 'bg-rose-700 hover:bg-rose-800 active:bg-rose-900',
        }
      );
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
