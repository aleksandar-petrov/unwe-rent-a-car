package bg.unwe.aleksandarpetrov.rentacar.service.impl;

import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.ExportType;
import bg.unwe.aleksandarpetrov.rentacar.repository.CarRepository;
import bg.unwe.aleksandarpetrov.rentacar.repository.RentalRepository;
import bg.unwe.aleksandarpetrov.rentacar.repository.UserRepository;
import bg.unwe.aleksandarpetrov.rentacar.service.ExportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@SuppressWarnings("rawtypes")
@Service
public class ExportServiceImpl implements ExportService {

  private final ObjectMapper objectMapper;

  private final Map<ExportType, JpaRepository> exportTypeRepositoryMap;

  public ExportServiceImpl(
      ObjectMapper objectMapper,
      UserRepository userRepository,
      CarRepository carRepository,
      RentalRepository rentalRepository) {
    this.objectMapper = objectMapper;

    this.exportTypeRepositoryMap =
        Map.of(
            ExportType.USERS,
            userRepository,
            ExportType.CARS,
            carRepository,
            ExportType.RENTALS,
            rentalRepository);
  }

  @Override
  public String export(ExportType exportType) {
    return exportJson(exportTypeRepositoryMap.get(exportType));
  }

  private String exportJson(JpaRepository repository) {
    try {
      var result = repository.findAll();
      return objectMapper.writeValueAsString(result);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
