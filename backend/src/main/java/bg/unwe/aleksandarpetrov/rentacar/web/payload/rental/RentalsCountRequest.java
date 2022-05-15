package bg.unwe.aleksandarpetrov.rentacar.web.payload.rental;

import bg.unwe.aleksandarpetrov.rentacar.service.model.search.RentalsSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalsCountRequest implements RentalsSearch {

  private String renterId;

  private String ownerId;
}
