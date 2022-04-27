package bg.unwe.aleksandarpetrov.rentacar.exception;

import bg.unwe.aleksandarpetrov.rentacar.exception.base.BaseInvalidCustomException;

public class InvalidPhotoException extends BaseInvalidCustomException {

  public InvalidPhotoException(String message) {
    super(message);
  }
}
