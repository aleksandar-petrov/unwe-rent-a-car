package bg.unwe.aleksandarpetrov.rentacar.exception;

import bg.unwe.aleksandarpetrov.rentacar.exception.base.BaseInvalidCustomException;

public class InvalidUserException extends BaseInvalidCustomException {

  public InvalidUserException(String message) {
    super(message);
  }
}
