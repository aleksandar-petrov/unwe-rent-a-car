package bg.unwe.aleksandarpetrov.rentacar.web.payload.photo;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PhotoPatchRequest {

  @JsonProperty("isUploaded")
  @NotNull
  private Boolean isUploaded;
}
