package bg.unwe.aleksandarpetrov.rentacar.service;

import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarCreateRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarResponse;
import org.springframework.data.domain.Page;

public interface CarService {

  CarResponse create(CarCreateRequest model, String userId);

  Page<CarResponse> findAllByOwnerId(String ownerId, int page);

  CarResponse findById(String id);
}
