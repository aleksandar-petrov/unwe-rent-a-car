package bg.unwe.aleksandarpetrov.rentacar.service;

import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.ExportType;

public interface ExportService {

  String export(ExportType exportType);
}
