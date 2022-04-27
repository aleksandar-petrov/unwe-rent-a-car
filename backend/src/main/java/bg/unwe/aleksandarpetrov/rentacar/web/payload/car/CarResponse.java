package bg.unwe.aleksandarpetrov.rentacar.web.payload.car;

import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.CarTransmission;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.photo.PhotoResponse;
import java.util.List;
import lombok.Data;

@Data
public class CarResponse {

  private String id;

  private Integer year;

  private String make;

  private String model;

  private Long mileage;

  private CarTransmission transmission;

  private List<PhotoResponse> photos;

  private OwnerResponse owner;
}
