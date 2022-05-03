package bg.unwe.aleksandarpetrov.rentacar.web.payload.car;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarSearchRequest {

  private String country;

  private String city;

  private String make;

  private String model;

  private Integer minYear;

  private Integer maxYear;

  private Long minMileage;

  private Long maxMileage;

  private BigDecimal minPricePerDay;

  private BigDecimal maxPricePerDay;

  private String sortBy;

  private String sortDirection;
}

