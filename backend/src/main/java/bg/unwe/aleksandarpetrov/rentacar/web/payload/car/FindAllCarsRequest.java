package bg.unwe.aleksandarpetrov.rentacar.web.payload.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FindAllCarsRequest {

  private int page;

  private String ownerId;

  private String userId;

  private CarSearchRequest search;
}
