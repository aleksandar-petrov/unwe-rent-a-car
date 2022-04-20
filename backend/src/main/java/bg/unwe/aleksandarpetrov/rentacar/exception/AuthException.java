package bg.unwe.aleksandarpetrov.rentacar.exception;

import bg.unwe.aleksandarpetrov.rentacar.exception.base.BaseCustomException;
import org.springframework.http.HttpStatus;

public class AuthException extends BaseCustomException {

  public AuthException(String message) {
    super(HttpStatus.UNAUTHORIZED.value(), message);
  }
}
