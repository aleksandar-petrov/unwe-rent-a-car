package bg.unwe.aleksandarpetrov.rentacar.web.payload.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterRequest {

  @NotBlank private String firstName;

  @NotBlank private String lastName;

  @NotBlank @Email private String email;

  @NotBlank private String password;

  @NotBlank private String confirmPassword;

  @NotBlank
  @Pattern(regexp = "^(([+]?359)|0)8[789]\\d{7}$")
  private String phoneNumber;
}
