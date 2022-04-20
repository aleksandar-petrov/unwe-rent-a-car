package bg.unwe.aleksandarpetrov.rentacar.web.error;

import bg.unwe.aleksandarpetrov.rentacar.exception.base.BaseCustomException;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.error.ErrorResponse;
import com.google.common.base.CaseFormat;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<ErrorResponse> handleException(Exception incomeException) {
    var response = new ErrorResponse();
    response.setReason(this.getUppercaseUnderscoreExceptionName(incomeException.getClass()));
    if (incomeException instanceof BaseCustomException) {
      var exception = (BaseCustomException) incomeException;

      if (incomeException.getMessage() != null) {
        response.setMessage(incomeException.getMessage());
      }
      response.setStatusCode(exception.getStatusCode());
    } else if (incomeException instanceof MethodArgumentNotValidException) {
      var exception = (MethodArgumentNotValidException) incomeException;

      response
          .getErrors()
          .addAll(
              exception.getFieldErrors().stream()
                  .sorted(Comparator.comparing(FieldError::getField))
                  .map(
                      e ->
                          e.getField().toUpperCase()
                              + "_"
                              + Objects.requireNonNull(e.getDefaultMessage())
                                  .toUpperCase()
                                  .replaceAll("[ \\-]", "_"))
                  .collect(Collectors.toList()));

      response.setMessage(
          exception.getFieldErrors().stream()
              .sorted(Comparator.comparing(FieldError::getField))
              .map(e -> e.getField() + " " + e.getDefaultMessage() + ".")
              .collect(Collectors.joining(" ")));

      response.setStatusCode(HttpStatus.BAD_REQUEST.value());
    } else if (incomeException instanceof HttpMessageNotReadableException) {
      response.setStatusCode(HttpStatus.BAD_REQUEST.value());
    } else {
      if (incomeException instanceof AccessDeniedException) {
        response.setStatusCode(HttpStatus.FORBIDDEN.value());
      } else {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      }
    }

    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  private String getUppercaseUnderscoreExceptionName(Class<? extends Exception> aClass) {
    return CaseFormat.UPPER_CAMEL.to(
        CaseFormat.UPPER_UNDERSCORE, aClass.getSimpleName().replace("Exception", ""));
  }
}
