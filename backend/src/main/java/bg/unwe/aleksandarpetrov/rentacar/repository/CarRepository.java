package bg.unwe.aleksandarpetrov.rentacar.repository;

import bg.unwe.aleksandarpetrov.rentacar.entity.Car;
import bg.unwe.aleksandarpetrov.rentacar.repository.model.CountryCityView;
import bg.unwe.aleksandarpetrov.rentacar.repository.model.MinMaxSearchView;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CarRepository extends JpaRepository<Car, String>, QuerydslPredicateExecutor<Car> {

  @Query(
      "SELECT new bg.unwe.aleksandarpetrov.rentacar.repository.model.MinMaxSearchView(MIN(c.year), MAX(c.year), MIN(c.mileage), MAX(c.mileage), MIN(c.pricePerDay), MAX(c.pricePerDay)) FROM Car c")
  MinMaxSearchView getMinMaxSearchView();

  @Query(
      "SELECT new bg.unwe.aleksandarpetrov.rentacar.repository.model.CountryCityView(c.location.country, c.location.city) FROM Car c GROUP BY c.location.country, c.location.city")
  List<CountryCityView> findAllGroupedByCountryAndCity();

  @Query(
      "SELECT new bg.unwe.aleksandarpetrov.rentacar.repository.model.CountryCityView(c.make, c.model) FROM Car c GROUP BY c.make, c.model")
  List<CountryCityView> findAllGroupedByMakeAndModel();
}
