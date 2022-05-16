package bg.unwe.aleksandarpetrov.rentacar.web.payload.rental;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalsFinancialStatsResponse {

  private BigDecimal finishedRentalsTotalPrice;
}
