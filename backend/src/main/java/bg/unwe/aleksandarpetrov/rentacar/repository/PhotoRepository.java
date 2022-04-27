package bg.unwe.aleksandarpetrov.rentacar.repository;

import bg.unwe.aleksandarpetrov.rentacar.entity.Photo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, String> {

  List<Photo> findAllByIsAssignedIsFalse();

  List<Photo> findAllByIdInAndIsAssignedIsFalse(List<String> ids);
}
