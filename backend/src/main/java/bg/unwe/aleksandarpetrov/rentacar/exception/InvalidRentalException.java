package bg.unwe.aleksandarpetrov.rentacar.exception;

import bg.unwe.aleksandarpetrov.rentacar.exception.base.BaseInvalidCustomException;

public class InvalidRentalException extends BaseInvalidCustomException {

  public InvalidRentalException(String message) {
    super(message);
  }
}
