package bg.unwe.aleksandarpetrov.rentacar.repository.model;

import bg.unwe.aleksandarpetrov.rentacar.service.model.search.KeyValueSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CountryCityView implements KeyValueSearch {

  private String country;

  private String city;

  @Override
  public String getKey() {
    return country;
  }

  @Override
  public String getValue() {
    return city;
  }
}
