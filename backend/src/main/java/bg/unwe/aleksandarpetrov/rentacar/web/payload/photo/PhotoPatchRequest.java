package bg.unwe.aleksandarpetrov.rentacar.web.payload.photo;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PhotoPatchRequest {

  @NotNull private Boolean isUploaded;
}
