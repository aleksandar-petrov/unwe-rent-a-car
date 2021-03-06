package bg.unwe.aleksandarpetrov.rentacar.web.payload.rental;

import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.RentalStatus;
import bg.unwe.aleksandarpetrov.rentacar.service.model.search.RentalsSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FindAllRentalsRequest implements RentalsSearch {

  private int page = 1;

  private String renterId;

  private String ownerId;

  private String carId;

  private Boolean isRentalRequest;

  private String rentalId;

  private RentalStatus status;
}
