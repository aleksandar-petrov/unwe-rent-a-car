package bg.unwe.aleksandarpetrov.rentacar.exception;

import bg.unwe.aleksandarpetrov.rentacar.exception.base.BaseInvalidCustomException;

public class DuplicateEmailException extends BaseInvalidCustomException {

  public DuplicateEmailException(String message) {
    super(message);
  }
}
