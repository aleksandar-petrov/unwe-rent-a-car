import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableComponent } from './table.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [TableComponent],
  exports: [TableComponent],
  imports: [CommonModule, FontAwesomeModule],
})
export class TableModule {}
