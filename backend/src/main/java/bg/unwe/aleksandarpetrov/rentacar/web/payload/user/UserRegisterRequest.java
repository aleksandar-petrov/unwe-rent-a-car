package bg.unwe.aleksandarpetrov.rentacar.web.payload.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterRequest {

  @NotBlank private String firstName;

  @NotBlank private String lastName;

  @NotBlank @Email private String email;

  @NotBlank private String password;

  @NotBlank private String confirmPassword;
}
