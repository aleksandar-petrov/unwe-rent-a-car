package bg.unwe.aleksandarpetrov.rentacar.web.payload.location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationCreateRequest {

  @NotNull private Double latitude;

  @NotNull private Double longitude;

  @NotBlank private String address;

  @NotBlank private String city;

  @NotBlank private String country;
}
