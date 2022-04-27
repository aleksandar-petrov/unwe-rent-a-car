package bg.unwe.aleksandarpetrov.rentacar.service.impl;

import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.INVALID_CAR;
import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.INVALID_USER;

import bg.unwe.aleksandarpetrov.rentacar.exception.InvalidCarException;
import bg.unwe.aleksandarpetrov.rentacar.exception.InvalidUserException;
import bg.unwe.aleksandarpetrov.rentacar.repository.CarRepository;
import bg.unwe.aleksandarpetrov.rentacar.repository.PhotoRepository;
import bg.unwe.aleksandarpetrov.rentacar.repository.UserRepository;
import bg.unwe.aleksandarpetrov.rentacar.service.CarService;
import bg.unwe.aleksandarpetrov.rentacar.service.MappingService;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarCreateRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

  private final CarRepository carRepository;

  private final PhotoRepository photoRepository;

  private final UserRepository userRepository;

  private final MappingService mappingService;

  @Override
  public CarResponse create(CarCreateRequest model, String userId) {
    var car = mappingService.toCar(model);

    var photos = photoRepository.findAllByIdInAndIsAssignedIsFalse(model.getPhotosIds());
    photos.forEach(photo -> photo.setIsAssigned(true));
    photos = photoRepository.saveAll(photos);

    car.setPhotos(photos);

    car.setOwner(
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidUserException(String.format(INVALID_USER, userId))));

    car = carRepository.save(car);

    return mappingService.toCarResponse(car);
  }

  @Override
  public Page<CarResponse> findAllByOwnerId(String ownerId, int page) {
    var carsPage = carRepository.findAllByOwnerId(ownerId, PageRequest.of(page - 1, 6));

    return carsPage.map(mappingService::toCarResponse);
  }

  @Override
  public CarResponse findById(String id) {
    return mappingService.toCarResponse(
        carRepository
            .findById(id)
            .orElseThrow(() -> new InvalidCarException(String.format(INVALID_CAR, id))));
  }
}
