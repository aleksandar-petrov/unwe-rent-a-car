package bg.unwe.aleksandarpetrov.rentacar.web.payload.car;

import lombok.Data;

@Data
public class OwnerResponse {

  private String id;

  private String firstName;

  private String lastName;

  private String phoneNumber;
}
