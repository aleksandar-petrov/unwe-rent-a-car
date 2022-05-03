import {
  AfterViewInit,
  Directive,
  ElementRef,
  EventEmitter,
  Input,
  OnDestroy,
  Output,
} from '@angular/core';
import Viewer from 'viewerjs';

@Directive({
  selector: '[racImageViewer]',
})
export class ImageViewerDirective implements AfterViewInit, OnDestroy {
  @Input() viewerOptions: Viewer.Options = {};

  @Output() viewerReady: EventEmitter<Event> = new EventEmitter<Event>();
  @Output() viewerShow: EventEmitter<Event> = new EventEmitter<Event>();
  @Output() viewerShown: EventEmitter<Event> = new EventEmitter<Event>();
  @Output() viewerHide: EventEmitter<Event> = new EventEmitter<Event>();
  @Output() viewerHidden: EventEmitter<Event> = new EventEmitter<Event>();
  @Output() viewerView: EventEmitter<Event> = new EventEmitter<Event>();
  @Output() viewerViewed: EventEmitter<Event> = new EventEmitter<Event>();
  @Output() viewerZoom: EventEmitter<Event> = new EventEmitter<Event>();
  @Output() viewerZoomed: EventEmitter<Event> = new EventEmitter<Event>();

  instance: Viewer | undefined;

  private readonly nativeElement: HTMLElement;

  constructor(private elementRef: ElementRef) {
    this.nativeElement = this.elementRef.nativeElement;
  }

  ngAfterViewInit(): void {
    this.initViewer();
  }

  ngOnDestroy(): void {
    if (this.instance) {
      this.instance.destroy();
    }
  }

  private initViewer(): void {
    if (this.instance) {
      this.instance.destroy();
    }

    this.instance = new Viewer(this.nativeElement, {
      toolbar: false,
      navbar: false,
      tooltip: false,
      title: false,
      transition: true,
      ...this.viewerOptions,
    });

    this.nativeElement.addEventListener(
      'ready',
      (event) => this.viewerReady.emit(event),
      false
    );
    this.nativeElement.addEventListener(
      'show',
      (event) => this.viewerShow.emit(event),
      false
    );
    this.nativeElement.addEventListener(
      'shown',
      (event) => this.viewerShown.emit(event),
      false
    );
    this.nativeElement.addEventListener(
      'hide',
      (event) => this.viewerHide.emit(event),
      false
    );
    this.nativeElement.addEventListener(
      'hidden',
      (event) => this.viewerHidden.emit(event),
      false
    );
    this.nativeElement.addEventListener(
      'view',
      (event) => this.viewerView.emit(event),
      false
    );
    this.nativeElement.addEventListener(
      'viewed',
      (event) => this.viewerViewed.emit(event),
      false
    );
    this.nativeElement.addEventListener(
      'zoom',
      (event) => this.viewerZoom.emit(event),
      false
    );
    this.nativeElement.addEventListener(
      'zoomed',
      (event) => this.viewerZoomed.emit(event),
      false
    );
  }
}
