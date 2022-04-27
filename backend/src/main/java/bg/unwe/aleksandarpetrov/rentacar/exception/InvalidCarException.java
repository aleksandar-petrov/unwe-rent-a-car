package bg.unwe.aleksandarpetrov.rentacar.exception;

import bg.unwe.aleksandarpetrov.rentacar.exception.base.BaseInvalidCustomException;

public class InvalidCarException extends BaseInvalidCustomException {

  public InvalidCarException(String message) {
    super(message);
  }
}
