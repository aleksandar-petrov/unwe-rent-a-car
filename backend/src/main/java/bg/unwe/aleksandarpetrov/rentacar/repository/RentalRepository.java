package bg.unwe.aleksandarpetrov.rentacar.repository;

import bg.unwe.aleksandarpetrov.rentacar.entity.Rental;
import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.RentalStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RentalRepository
    extends JpaRepository<Rental, String>, QuerydslPredicateExecutor<Rental> {

  Optional<Rental> findFirstByStatusAndCarIdAndRenterId(
      RentalStatus status, String carId, String userId);

  Optional<Rental> findFirstByIdAndRenterId(String id, String renterId);

  Optional<Rental> findFirstByIdAndCarOwnerId(String id, String ownerId);

  @Query(
      "SELECT SUM(r.totalPrice) FROM Rental r WHERE r.status = bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.RentalStatus.FINISHED")
  BigDecimal findFinishedRentalsTotalPrice();

  List<Rental> findAllByStatusAndRentedFrom(RentalStatus status, LocalDate rentedFrom);

  List<Rental> findAllByStatusAndRentedTo(RentalStatus status, LocalDate rentedFrom);
}
