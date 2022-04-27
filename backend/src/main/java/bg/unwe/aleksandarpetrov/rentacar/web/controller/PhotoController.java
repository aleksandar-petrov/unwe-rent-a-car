package bg.unwe.aleksandarpetrov.rentacar.web.controller;

import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.PhotoType;
import bg.unwe.aleksandarpetrov.rentacar.service.PhotoService;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.photo.PhotoPatchRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.photo.PhotoUploadResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/photos")
public class PhotoController {

  private final PhotoService photoService;

  @PostMapping
  public PhotoUploadResponse create(@RequestParam() PhotoType type) {
    return photoService.create(type);
  }

  @PatchMapping("/{id}")
  public void patch(@PathVariable String id, @RequestBody @NotNull @Valid PhotoPatchRequest model) {
    photoService.patch(id, model);
  }
}
