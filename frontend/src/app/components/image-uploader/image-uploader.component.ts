import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faTrash, faUpload } from '@fortawesome/free-solid-svg-icons';
import { PhotoService } from '../../services/photo.service';
import { PhotoProgress, PhotoResponse } from '../../models/photo.model';

@Component({
  selector: 'rac-image-uploader',
  templateUrl: './image-uploader.component.html',
  styleUrls: ['./image-uploader.component.scss'],
})
export class ImageUploaderComponent implements OnInit {
  faUpload = faUpload;
  faTrash = faTrash;

  @Input() images: PhotoResponse[] = [];
  @Output() onImageChange = new EventEmitter<PhotoProgress[]>();

  uploadedImages: PhotoProgress[] = [];

  constructor(private photoService: PhotoService) {}

  ngOnInit(): void {
    this.uploadedImages = this.uploadedImages.concat(
      this.images.map((i) => {
        return {
          progress: 100,
          photo: { id: i.id, url: i.url },
        } as PhotoProgress;
      })
    );

    if (this.uploadedImages.length > 0) {
      this.onImageChange.emit(this.uploadedImages);
    }
  }

  handleFilesSelected(event: any) {
    const files = [...event?.target?.files] as File[];

    files.forEach(async (f) => {
      const progressSub = await this.photoService.uploadImage(f);
      progressSub.subscribe((progress) => {
        const index = this.uploadedImages.findIndex(
          (i) => i.photo.id === progress.photo.id
        );
        if (index === -1) {
          this.uploadedImages = this.uploadedImages.concat(progress);
          this.onImageChange.emit(this.uploadedImages);
          return;
        }

        const isCurrentInProgress = this.uploadedImages[index].progress < 100;

        this.uploadedImages[index] = progress;
        this.uploadedImages = [...this.uploadedImages];

        const isNewInProgress = progress.progress < 100;
        if (isCurrentInProgress === isNewInProgress) {
          return;
        }

        this.onImageChange.emit(this.uploadedImages);
      });
    });
  }

  handleImageDelete(id: string) {
    this.uploadedImages = this.uploadedImages.filter((i) => i.photo.id !== id);
    this.onImageChange.emit(this.uploadedImages);
  }
}
