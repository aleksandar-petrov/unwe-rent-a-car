package bg.unwe.aleksandarpetrov.rentacar.exception.base;

import org.springframework.http.HttpStatus;

public abstract class BaseInvalidCustomException extends BaseCustomException {

  public BaseInvalidCustomException() {
    super(HttpStatus.BAD_REQUEST.value());
  }

  public BaseInvalidCustomException(String message) {
    super(HttpStatus.BAD_REQUEST.value(), message);
  }
}
