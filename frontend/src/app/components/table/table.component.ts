import { Component, Input, OnInit } from '@angular/core';
import { IconDefinition } from '@fortawesome/fontawesome-common-types';

@Component({
  selector: 'rac-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
export class TableComponent implements OnInit {
  @Input() headers: string[] = [];
  @Input() rows: string[][] = [];
  @Input() actionRows: Action[][] = [];

  constructor() {}

  get shouldShowActions(): boolean {
    return this.actionRows.some((x) => x.length > 0);
  }

  ngOnInit(): void {}
}

export interface Action {
  icon: IconDefinition;
  handler: () => void;
  class: string;
}
