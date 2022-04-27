export interface PhotoUploadResponse {
  id: string;
  url: string;
  presignedUploadUrl: string;
}

export enum PhotoType {
  JPEG = 'JPEG',
  PNG = 'PNG',
  GIF = 'GIF',
}

export interface PhotoResponse {
  id: string;
  url: string;
}

export interface PhotoProgress {
  progress: number;
  photo: {
    id: string;
    url: string | null;
    base64: string;
  };
}

export interface PhotoPatchRequest {
  isUploaded: boolean;
}
