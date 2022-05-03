package bg.unwe.aleksandarpetrov.rentacar.repository.model;

import bg.unwe.aleksandarpetrov.rentacar.service.model.search.KeyValueSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MakeModelView implements KeyValueSearch {

  private String make;

  private String model;

  @Override
  public String getKey() {
    return make;
  }

  @Override
  public String getValue() {
    return model;
  }
}
