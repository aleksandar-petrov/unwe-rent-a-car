package bg.unwe.aleksandarpetrov.rentacar.service.impl;

import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.INVALID_PHOTO;

import bg.unwe.aleksandarpetrov.rentacar.entity.Photo;
import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.PhotoType;
import bg.unwe.aleksandarpetrov.rentacar.exception.InvalidPhotoException;
import bg.unwe.aleksandarpetrov.rentacar.repository.PhotoRepository;
import bg.unwe.aleksandarpetrov.rentacar.service.MappingService;
import bg.unwe.aleksandarpetrov.rentacar.service.PhotoService;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.photo.PhotoPatchRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.photo.PhotoUploadResponse;
import java.time.Duration;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@RequiredArgsConstructor
@Service
public class PhotoServiceImpl implements PhotoService {

  private static final String BUCKET_NAME = "unwe-rac-photos";

  private static final String BUCKET_DOMAIN = "https://unwe-rac-photos.s3.eu-west-2.amazonaws.com/";

  private final S3Presigner s3Presigner;

  private final PhotoRepository photoRepository;

  private final MappingService mappingService;

  private final S3AsyncClient s3Client;

  @Override
  public PhotoUploadResponse create(PhotoType type) {
    var id = UUID.randomUUID().toString();
    var key = id + "." + type.name().toLowerCase();

    var presignedUploadUrl = getPresignedUploadUrl(key, type);
    var photoUrl = BUCKET_DOMAIN + key;

    photoRepository.save(new Photo(id, photoUrl, false, false));

    return new PhotoUploadResponse(id, photoUrl, presignedUploadUrl);
  }

  @Override
  public void patch(String id, PhotoPatchRequest model) {
    var photo =
        photoRepository
            .findById(id)
            .orElseThrow(() -> new InvalidPhotoException(String.format(INVALID_PHOTO, id)));

    mappingService.update(model, photo);

    photoRepository.save(photo);
  }

  @Scheduled(cron = "0 0 13 * * *")
  @Override
  public void deleteUnusedPhotos() {
    var unusedPhotos = photoRepository.findAllByIsAssignedIsFalse();
    var uploadedUnusedPhotosIdentifiers =
        unusedPhotos.stream()
            .filter(photo -> !photo.getIsAssigned() && photo.getIsUploaded())
            .map(photo -> photo.getUrl().replace(BUCKET_DOMAIN, ""))
            .map(key -> ObjectIdentifier.builder().key(key).build())
            .collect(Collectors.toList());

    if (!uploadedUnusedPhotosIdentifiers.isEmpty()) {
      var deleteObjectsRequest =
          DeleteObjectsRequest.builder()
              .delete(Delete.builder().objects(uploadedUnusedPhotosIdentifiers).build())
              .bucket(BUCKET_NAME)
              .build();

      s3Client.deleteObjects(deleteObjectsRequest);
    }

    photoRepository.deleteAll(unusedPhotos);
  }

  private String getPresignedUploadUrl(String id, PhotoType type) {
    var putObjectRequest =
        PutObjectRequest.builder()
            .bucket(BUCKET_NAME)
            .key(id)
            .contentType(type.getContentType())
            .build();

    var putObjectPresignRequest =
        PutObjectPresignRequest.builder()
            .putObjectRequest(putObjectRequest)
            .signatureDuration(Duration.ofMinutes(5))
            .build();

    final PresignedPutObjectRequest presignedPutObjectRequest =
        s3Presigner.presignPutObject(putObjectPresignRequest);

    return presignedPutObjectRequest.url().toString();
  }
}
