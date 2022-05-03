package bg.unwe.aleksandarpetrov.rentacar.web.payload.car;

import bg.unwe.aleksandarpetrov.rentacar.web.payload.location.CountryResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CarSearchResponse {

  private List<CountryResponse> countries;

  private List<MakeResponse> makes;

  private Integer minYear;

  private Integer maxYear;

  private Long minMileage;

  private Long maxMileage;

  private BigDecimal minPricePerDay;

  private BigDecimal maxPricePerDay;
}
