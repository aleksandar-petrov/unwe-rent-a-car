package bg.unwe.aleksandarpetrov.rentacar.exception;

import bg.unwe.aleksandarpetrov.rentacar.exception.base.BaseInvalidCustomException;

public class PasswordMismatchException extends BaseInvalidCustomException {

  public PasswordMismatchException(String message) {
    super(message);
  }
}
