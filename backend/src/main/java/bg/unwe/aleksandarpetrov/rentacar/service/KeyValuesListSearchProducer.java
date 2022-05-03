package bg.unwe.aleksandarpetrov.rentacar.service;

import bg.unwe.aleksandarpetrov.rentacar.service.model.search.KeyValuesListSearch;

public interface KeyValuesListSearchProducer<KVLS extends KeyValuesListSearch> {

  KVLS produce();
}
