package bg.unwe.aleksandarpetrov.rentacar.web.payload.user;

import lombok.Data;

@Data
public class UserLoginRequest {

  private String email;

  private String password;
}
