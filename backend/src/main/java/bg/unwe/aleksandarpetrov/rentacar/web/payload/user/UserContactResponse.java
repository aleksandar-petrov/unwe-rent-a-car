package bg.unwe.aleksandarpetrov.rentacar.web.payload.user;

import lombok.Data;

@Data
public class UserContactResponse {

  private String id;

  private String firstName;

  private String lastName;

  private String phoneNumber;
}
