package bg.unwe.aleksandarpetrov.rentacar.web.payload.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnyUserExistsRequest {

  private String email;

  private String phoneNumber;
}
