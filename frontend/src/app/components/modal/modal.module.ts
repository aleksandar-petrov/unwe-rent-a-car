import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalComponent } from './modal.component';
import { NgxSmartModalModule } from 'ngx-smart-modal';

@NgModule({
  declarations: [ModalComponent],
  imports: [CommonModule, NgxSmartModalModule],
  exports: [ModalComponent],
})
export class ModalModule {}
