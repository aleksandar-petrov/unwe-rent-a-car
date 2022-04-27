package bg.unwe.aleksandarpetrov.rentacar.web.payload.photo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhotoUploadResponse {

  private String id;

  private String url;

  private String presignedUploadUrl;
}
