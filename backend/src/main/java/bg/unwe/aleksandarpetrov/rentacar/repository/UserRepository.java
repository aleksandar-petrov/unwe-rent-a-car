package bg.unwe.aleksandarpetrov.rentacar.repository;

import bg.unwe.aleksandarpetrov.rentacar.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository
    extends JpaRepository<User, String>, QuerydslPredicateExecutor<User> {

  Optional<User> findFirstByEmail(String email);
}
