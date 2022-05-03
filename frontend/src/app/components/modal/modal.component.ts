import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgxSmartModalService } from 'ngx-smart-modal';

@Component({
  selector: 'rac-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss'],
})
export class ModalComponent implements OnInit {
  @Input() identifier: string = '';
  @Input() class: string = '';
  @Input() fullWidth: boolean = false;
  @Output() onClose = new EventEmitter<void>();

  constructor(private modalService: NgxSmartModalService) {}

  ngOnInit(): void {}

  open() {
    this.modalService.open(this.identifier);
  }

  close() {
    this.modalService.close(this.identifier);
  }
}
