package bg.unwe.aleksandarpetrov.rentacar.service;

import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarCreateRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarSearchResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.FindAllCarsRequest;
import org.springframework.data.domain.Page;

public interface CarService {

  CarResponse create(CarCreateRequest model, String userId);

  Page<CarResponse> findAll(FindAllCarsRequest model);

  CarResponse findById(String id);

  void edit(String id, CarCreateRequest model);

  CarSearchResponse getCarSearch();
}
