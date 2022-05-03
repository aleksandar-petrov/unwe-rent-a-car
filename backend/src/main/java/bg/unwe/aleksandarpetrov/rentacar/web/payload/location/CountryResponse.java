package bg.unwe.aleksandarpetrov.rentacar.web.payload.location;

import bg.unwe.aleksandarpetrov.rentacar.service.model.search.KeyValuesListSearch;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CountryResponse implements KeyValuesListSearch {

  private String name;

  private List<String> cities;

  @Override
  public void setKey(String key) {
    name = key;
  }

  @Override
  public void setList(List<String> values) {
    cities = values;
  }
}
