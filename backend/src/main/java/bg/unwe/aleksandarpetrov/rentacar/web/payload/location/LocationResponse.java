package bg.unwe.aleksandarpetrov.rentacar.web.payload.location;

import lombok.Data;

@Data
public class LocationResponse {

  private Double latitude;

  private Double longitude;

  private String address;

  private String city;

  private String country;
}
