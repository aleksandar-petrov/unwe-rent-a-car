package bg.unwe.aleksandarpetrov.rentacar.service.impl;

import bg.unwe.aleksandarpetrov.rentacar.repository.CarRepository;
import bg.unwe.aleksandarpetrov.rentacar.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("authService")
public class AuthServiceImpl implements AuthService {

  private final CarRepository carRepository;

  @Override
  public boolean isUserOwnerOfCar(String userId, String carId) {
    var carCandidate = carRepository.findById(carId);
    if (carCandidate.isEmpty()) {
      return false;
    }

    return carCandidate.get().getOwner().getId().equals(userId);
  }
}
