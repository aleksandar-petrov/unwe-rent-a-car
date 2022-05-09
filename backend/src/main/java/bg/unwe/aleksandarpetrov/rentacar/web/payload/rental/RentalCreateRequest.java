package bg.unwe.aleksandarpetrov.rentacar.web.payload.rental;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RentalCreateRequest {

  @NotNull private LocalDate rentedFrom;

  @NotNull private LocalDate rentedTo;

  @NotBlank private String carId;
}
