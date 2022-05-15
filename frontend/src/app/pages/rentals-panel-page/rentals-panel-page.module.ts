import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RentalsPanelPageComponent } from './rentals-panel-page.component';
import { RouterModule } from '@angular/router';
import { TableModule } from '../../components/table/table.module';
import { PaginatorModule } from '../../components/paginator/paginator.module';
import { ModalModule } from '../../components/modal/modal.module';

@NgModule({
  declarations: [RentalsPanelPageComponent],
  imports: [
    CommonModule,
    RouterModule,
    TableModule,
    PaginatorModule,
    ModalModule,
  ],
  providers: [DatePipe],
})
export class RentalsPanelPageModule {}
