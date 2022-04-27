import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { Page } from '../../models/page.model';

@Component({
  selector: 'rac-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.scss'],
})
export class PaginatorComponent implements OnInit, OnChanges {
  @Input() page: Page<any> | undefined;
  @Output() onPageChange = new EventEmitter<number>();
  pageNumbers: number[] = [];

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(changes: SimpleChanges): void {
    this.generatePageNumbers();
  }

  handlePageChange(number: number) {
    if (!this.page) {
      return;
    }

    this.page.number = number - 1;
    this.generatePageNumbers();
    this.onPageChange.emit(number);
  }

  private generatePageNumbers() {
    if (!this.page) {
      return;
    }

    const currentPageNumber = this.page.number + 1;
    let pagesArray = [currentPageNumber];

    if (this.page.totalPages <= 7) {
      this.pageNumbers = [...Array(this.page.totalPages).keys()]
        .map((n) => n + 1)
        .filter((n) => n !== 1 && n !== this.page!.totalPages);
      return;
    }

    let prevPagesToAdd = Math.max(0, Math.min(2, currentPageNumber - 2));
    let nextPagesToAdd = Math.max(
      0,
      Math.min(2, this.page.totalPages - 1 - currentPageNumber)
    );

    prevPagesToAdd += Math.max(0, 2 - nextPagesToAdd);
    if (currentPageNumber === this.page.totalPages) {
      prevPagesToAdd++;
    }

    nextPagesToAdd += Math.max(0, 2 - prevPagesToAdd);
    if (currentPageNumber === 1) {
      nextPagesToAdd++;
    }

    pagesArray = [...Array(prevPagesToAdd).keys()]
      .map((n) => currentPageNumber - (n + 1))
      .reverse()
      .concat(pagesArray)
      .concat(
        [...Array(nextPagesToAdd).keys()].map(
          (n) => currentPageNumber + (n + 1)
        )
      );

    this.pageNumbers = pagesArray.filter(
      (n) => n !== 1 && n !== this.page!.totalPages
    );
  }
}
