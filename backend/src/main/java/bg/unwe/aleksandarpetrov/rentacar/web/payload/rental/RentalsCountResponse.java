package bg.unwe.aleksandarpetrov.rentacar.web.payload.rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalsCountResponse {

  private Long rentalRequestsCount;

  private Long rentalsCount;
}
