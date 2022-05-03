package bg.unwe.aleksandarpetrov.rentacar.repository.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MinMaxSearchView {

  private Integer minYear;

  private Integer maxYear;

  private Long minMileage;

  private Long maxMileage;

  private BigDecimal minPricePerDay;

  private BigDecimal maxPricePerDay;
}
