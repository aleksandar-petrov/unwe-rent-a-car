package bg.unwe.aleksandarpetrov.rentacar.exception;

import bg.unwe.aleksandarpetrov.rentacar.exception.base.BaseInvalidCustomException;

public class DuplicatePhoneNumberException extends BaseInvalidCustomException {

  public DuplicatePhoneNumberException(String message) {
    super(message);
  }
}
