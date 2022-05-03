package bg.unwe.aleksandarpetrov.rentacar.web.payload.car;

import bg.unwe.aleksandarpetrov.rentacar.service.model.search.KeyValuesListSearch;
import java.util.List;
import lombok.Data;

@Data
public class MakeResponse implements KeyValuesListSearch {

  private String name;

  private List<String> models;

  @Override
  public void setKey(String key) {
    name = key;
  }

  @Override
  public void setList(List<String> values) {
    models = values;
  }
}
