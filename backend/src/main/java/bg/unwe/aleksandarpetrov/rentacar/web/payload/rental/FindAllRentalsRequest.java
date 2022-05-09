package bg.unwe.aleksandarpetrov.rentacar.web.payload.rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FindAllRentalsRequest {

  private int page;

  private String renterId;

  private String ownerId;

  private String carId;

  private boolean isRentalRequest;
}
