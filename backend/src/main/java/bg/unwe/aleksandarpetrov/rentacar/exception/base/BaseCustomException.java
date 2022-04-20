package bg.unwe.aleksandarpetrov.rentacar.exception.base;

public abstract class BaseCustomException extends RuntimeException {

  private final int statusCode;

  public BaseCustomException(int statusCode) {
    this.statusCode = statusCode;
  }

  public BaseCustomException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
