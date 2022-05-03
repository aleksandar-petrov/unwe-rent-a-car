package bg.unwe.aleksandarpetrov.rentacar.service;

import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.PhotoType;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.photo.PhotoPatchRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.photo.PhotoUploadResponse;
import java.util.List;

public interface PhotoService {

  PhotoUploadResponse create(PhotoType type);

  void patch(String id, PhotoPatchRequest model);

  void deleteUnusedPhotos();

  void deleteAll(List<String> ids);
}
