package bg.unwe.aleksandarpetrov.rentacar.service;

public interface AuthService {

  boolean isUserOwnerOfCar(String userId, String carId);
}
