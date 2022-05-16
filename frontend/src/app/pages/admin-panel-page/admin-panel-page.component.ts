import { Component, OnInit } from '@angular/core';
import { RentalService } from '../../services/rental.service';
import { RentalsFinancialStatsResponse } from '../../models/rental.model';
import { ExportType } from '../../models/export.model';
import { ExportService } from '../../services/export.service';

@Component({
  selector: 'rac-admin-panel-page',
  templateUrl: './admin-panel-page.component.html',
  styleUrls: ['./admin-panel-page.component.scss'],
})
export class AdminPanelPageComponent implements OnInit {
  financialStats: RentalsFinancialStatsResponse | undefined;
  profitText: string = '';
  ExportType = ExportType;

  constructor(
    private rentalService: RentalService,
    private exportService: ExportService
  ) {}

  ngOnInit(): void {
    this.rentalService.getFinancialStats().subscribe((financialStats) => {
      this.financialStats = financialStats;
      if (!financialStats.finishedRentalsTotalPrice) {
        return;
      }
      this.profitText = `2% x ${financialStats.finishedRentalsTotalPrice} $ = ${
        0.02 * financialStats.finishedRentalsTotalPrice
      } $`;
    });
  }

  handleExport(exportType: ExportType) {
    this.exportService.generate(exportType);
  }
}
