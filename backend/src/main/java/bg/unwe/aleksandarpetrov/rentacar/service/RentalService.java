package bg.unwe.aleksandarpetrov.rentacar.service;

import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.FindAllRentalsRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalCreateRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalResponse;
import org.springframework.data.domain.Page;

public interface RentalService {

  RentalResponse create(RentalCreateRequest model, String renterId);

  Page<RentalResponse> findAll(FindAllRentalsRequest model, String userId);
}
