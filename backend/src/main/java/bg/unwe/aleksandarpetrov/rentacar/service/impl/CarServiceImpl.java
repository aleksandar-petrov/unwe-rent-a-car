package bg.unwe.aleksandarpetrov.rentacar.service.impl;

import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.INVALID_CAR;
import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.INVALID_USER;

import bg.unwe.aleksandarpetrov.rentacar.entity.Car;
import bg.unwe.aleksandarpetrov.rentacar.entity.Photo;
import bg.unwe.aleksandarpetrov.rentacar.entity.QCar;
import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.CarStatus;
import bg.unwe.aleksandarpetrov.rentacar.exception.InvalidCarException;
import bg.unwe.aleksandarpetrov.rentacar.exception.InvalidUserException;
import bg.unwe.aleksandarpetrov.rentacar.repository.CarRepository;
import bg.unwe.aleksandarpetrov.rentacar.repository.PhotoRepository;
import bg.unwe.aleksandarpetrov.rentacar.repository.UserRepository;
import bg.unwe.aleksandarpetrov.rentacar.service.CarService;
import bg.unwe.aleksandarpetrov.rentacar.service.MappingService;
import bg.unwe.aleksandarpetrov.rentacar.service.PhotoService;
import bg.unwe.aleksandarpetrov.rentacar.service.TransliterationService;
import bg.unwe.aleksandarpetrov.rentacar.service.model.search.KeyValueSearch;
import bg.unwe.aleksandarpetrov.rentacar.service.model.search.KeyValuesListSearch;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarCreateRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarSearchResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.FindAllCarsRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.MakeResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.location.CountryResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

  private final CarRepository carRepository;

  private final PhotoRepository photoRepository;

  private final UserRepository userRepository;

  private final MappingService mappingService;

  private final PhotoService photoService;

  private final TransliterationService transliterationService;

  @Override
  public CarResponse create(CarCreateRequest model, String userId) {
    var car = mappingService.toCar(model);

    var photos = photoRepository.findAllByIdInAndIsAssignedIsFalse(model.getPhotosIds());
    photos.forEach(photo -> photo.setIsAssigned(true));
    photos = photoRepository.saveAll(photos);

    car.setPhotos(photos);

    car.setOwner(
        userRepository
            .findById(userId)
            .orElseThrow(() -> new InvalidUserException(String.format(INVALID_USER, userId))));
    car.getLocation()
        .setAddress(transliterationService.transliterate(car.getLocation().getAddress()));

    car = carRepository.save(car);

    return mappingService.toCarResponse(car);
  }

  @Override
  public Page<CarResponse> findAll(FindAllCarsRequest model) {
    var carsPage = getCarsPage(model);

    return carsPage.map(mappingService::toCarResponse);
  }

  private Page<Car> getCarsPage(FindAllCarsRequest model) {
    var carPath = QCar.car;

    var predicate = Expressions.TRUE.isTrue();
    if (model.getOwnerId() != null) {
      predicate = predicate.and(carPath.owner.id.eq(model.getOwnerId()));

      if (!model.getOwnerId().equals(model.getUserId())) {
        predicate = predicate.and(carPath.status.eq(CarStatus.PUBLIC));
      }
    } else {
      predicate = predicate.and(carPath.status.eq(CarStatus.PUBLIC));
    }

    var search = model.getSearch();
    predicate =
        predicate
            .and(eqOfNullable(search.getCountry(), carPath.location.country))
            .and(eqOfNullable(search.getCity(), carPath.location.city))
            .and(eqOfNullable(search.getMake(), carPath.make))
            .and(eqOfNullable(search.getModel(), carPath.model))
            .and(goeOfNullable(search.getMinYear(), carPath.year))
            .and(loeOfNullable(search.getMaxYear(), carPath.year))
            .and(goeOfNullable(search.getMinMileage(), carPath.mileage))
            .and(loeOfNullable(search.getMaxMileage(), carPath.mileage))
            .and(goeOfNullable(search.getMinPricePerDay(), carPath.pricePerDay))
            .and(loeOfNullable(search.getMaxPricePerDay(), carPath.pricePerDay));

    return carRepository.findAll(
        predicate,
        PageRequest.of(
            model.getPage() - 1, 6, sortOfNullable(search.getSortBy(), search.getSortDirection())));
  }

  @Override
  public CarResponse findById(String id) {
    return mappingService.toCarResponse(findCarById(id));
  }

  @Override
  public void edit(String id, CarCreateRequest model) {
    var car = findCarById(id);

    var photosIdsToDelete =
        car.getPhotos().stream()
            .map(Photo::getId)
            .filter(photoId -> !model.getPhotosIds().contains(photoId))
            .collect(Collectors.toList());

    car.getPhotos().removeIf(p -> photosIdsToDelete.contains(p.getId()));

    var newPhotos = photoRepository.findAllByIdInAndIsAssignedIsFalse(model.getPhotosIds());
    newPhotos.forEach(p -> p.setIsAssigned(true));
    photoRepository.saveAll(newPhotos);
    car.getPhotos().addAll(newPhotos);

    mappingService.update(model, car);
    car.getLocation()
        .setAddress(transliterationService.transliterate(car.getLocation().getAddress()));

    carRepository.save(car);

    photoService.deleteAll(photosIdsToDelete);
  }

  @Override
  public CarSearchResponse getCarSearch() {
    var countryAndCityViews = carRepository.findAllGroupedByCountryAndCity();
    var countries = getKeyValuesListSearch(countryAndCityViews, CountryResponse::new);

    var makeAndModelViews = carRepository.findAllGroupedByMakeAndModel();
    var makes = getKeyValuesListSearch(makeAndModelViews, MakeResponse::new);

    var minMaxSearchView = carRepository.getMinMaxSearchView();

    return mappingService.toCarSearchResponse(minMaxSearchView, countries, makes);
  }

  private <KVS extends KeyValueSearch, KVLS extends KeyValuesListSearch>
      List<KVLS> getKeyValuesListSearch(List<KVS> keyValueSearchList, Supplier<KVLS> supplier) {
    var map = new HashMap<String, List<String>>();
    keyValueSearchList.forEach(
        v -> {
          map.putIfAbsent(v.getKey(), new ArrayList<>());
          map.get(v.getKey()).add(v.getValue());
        });

    return map.keySet().stream()
        .map(
            k -> {
              var keyValuesListSearch = supplier.get();

              keyValuesListSearch.setKey(k);
              keyValuesListSearch.setList(map.get(k));

              return keyValuesListSearch;
            })
        .collect(Collectors.toList());
  }

  private Car findCarById(String id) {
    return carRepository
        .findById(id)
        .orElseThrow(() -> new InvalidCarException(String.format(INVALID_CAR, id)));
  }

  private Predicate eqOfNullable(String value, StringPath stringPath) {
    return ofNullable(value, () -> stringPath.eq(value));
  }

  private <T extends Number & Comparable<?>> Predicate goeOfNullable(
      T value, NumberPath<T> numberPath) {
    return ofNullable(value, () -> numberPath.goe(value));
  }

  private <T extends Number & Comparable<?>> Predicate loeOfNullable(
      T value, NumberPath<T> numberPath) {
    return ofNullable(value, () -> numberPath.loe(value));
  }

  private Predicate ofNullable(
      Object value, Supplier<BooleanExpression> booleanExpressionSupplier) {
    if (value == null) {
      return Expressions.TRUE.isTrue();
    }

    return booleanExpressionSupplier.get();
  }

  private Sort sortOfNullable(String sortBy, String sortDirection) {
    if (sortBy == null) {
      return Sort.unsorted();
    }

    var direction = Direction.ASC;
    if (sortDirection != null) {
      direction = Direction.fromString(sortDirection);
    }

    return Sort.by(direction, sortBy);
  }
}
