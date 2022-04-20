package bg.unwe.aleksandarpetrov.rentacar.repository;

import bg.unwe.aleksandarpetrov.rentacar.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

  List<Role> findAllByNameIn(List<String> names);
}
