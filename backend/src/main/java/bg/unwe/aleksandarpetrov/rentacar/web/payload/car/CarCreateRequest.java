package bg.unwe.aleksandarpetrov.rentacar.web.payload.car;

import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.CarStatus;
import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.CarTransmission;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.location.LocationCreateRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class CarCreateRequest {

  @NotNull
  @Min(1900)
  @Max(3000)
  private Integer year;

  @NotBlank private String make;

  @NotBlank private String model;

  @NotNull @Positive private Long mileage;

  @NotNull private CarTransmission transmission;

  private List<String> photosIds = new ArrayList<>();

  @NotNull @Positive private BigDecimal pricePerDay;

  @NotNull private CarStatus status;

  @NotNull @Valid private LocationCreateRequest location;
}
