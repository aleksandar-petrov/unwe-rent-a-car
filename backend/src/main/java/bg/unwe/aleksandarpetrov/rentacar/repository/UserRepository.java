package bg.unwe.aleksandarpetrov.rentacar.repository;

import bg.unwe.aleksandarpetrov.rentacar.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findFirstByEmail(String email);
}
