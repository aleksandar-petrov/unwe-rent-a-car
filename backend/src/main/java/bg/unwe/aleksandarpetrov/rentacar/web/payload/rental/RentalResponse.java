package bg.unwe.aleksandarpetrov.rentacar.web.payload.rental;

import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.RentalStatus;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.UserContactResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class RentalResponse {

  private String id;

  private LocalDate rentedFrom;

  private LocalDate rentedTo;

  private UserContactResponse renter;

  private RentalStatus status;

  private Long days;

  private BigDecimal pricePerDay;

  private BigDecimal totalPrice;

  private CarResponse car;
}
