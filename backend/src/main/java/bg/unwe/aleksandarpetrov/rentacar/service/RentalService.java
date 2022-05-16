package bg.unwe.aleksandarpetrov.rentacar.service;

import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.FindAllRentalsRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalCreateRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalsCountRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalsCountResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalsFinancialStatsResponse;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.data.domain.Page;

public interface RentalService {

  RentalResponse create(RentalCreateRequest model, String renterId);

  Page<RentalResponse> findAll(FindAllRentalsRequest model, String userId);

  RentalsCountResponse getCount(RentalsCountRequest model, String userId);

  void delete(String rentalId, String renterId);

  void reject(String rentalId, String ownerId);

  void approve(String rentalId, String ownerId);

  void updateOngoingRentals();

  Set<LocalDate> getRentalDates(String carId);

  RentalsFinancialStatsResponse getFinancialStats();
}
