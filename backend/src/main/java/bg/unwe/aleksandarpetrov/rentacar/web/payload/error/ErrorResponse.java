package bg.unwe.aleksandarpetrov.rentacar.web.payload.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@Data
public class ErrorResponse {

  private int statusCode;

  private String reason;

  private String message;

  private List<String> errors;

  public ErrorResponse() {
    errors = new ArrayList<>();
  }
}
