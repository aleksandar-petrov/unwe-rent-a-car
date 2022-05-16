package bg.unwe.aleksandarpetrov.rentacar.service.impl;

import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.INVALID_CAR;
import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.INVALID_RENTAL_OWNER;
import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.INVALID_RENTAL_RENTER;
import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.INVALID_USER;
import static java.time.temporal.ChronoUnit.DAYS;

import bg.unwe.aleksandarpetrov.rentacar.entity.QRental;
import bg.unwe.aleksandarpetrov.rentacar.entity.Rental;
import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.RentalStatus;
import bg.unwe.aleksandarpetrov.rentacar.exception.CarNotAvailableInProvidedDateRangeException;
import bg.unwe.aleksandarpetrov.rentacar.exception.ExistingPendingVerificationRentalException;
import bg.unwe.aleksandarpetrov.rentacar.exception.InvalidCarException;
import bg.unwe.aleksandarpetrov.rentacar.exception.InvalidDateRangeException;
import bg.unwe.aleksandarpetrov.rentacar.exception.InvalidRentalException;
import bg.unwe.aleksandarpetrov.rentacar.exception.InvalidUserException;
import bg.unwe.aleksandarpetrov.rentacar.exception.RentOwnedCarException;
import bg.unwe.aleksandarpetrov.rentacar.exception.RentalNotInPendingVerificationStatusException;
import bg.unwe.aleksandarpetrov.rentacar.repository.CarRepository;
import bg.unwe.aleksandarpetrov.rentacar.repository.RentalRepository;
import bg.unwe.aleksandarpetrov.rentacar.repository.UserRepository;
import bg.unwe.aleksandarpetrov.rentacar.service.MappingService;
import bg.unwe.aleksandarpetrov.rentacar.service.RentalService;
import bg.unwe.aleksandarpetrov.rentacar.service.model.search.RentalsSearch;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.FindAllRentalsRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalCreateRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalsCountRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalsCountResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RentalServiceImpl implements RentalService {

  private static final QRental RENTAL_PATH = QRental.rental;

  private static final Sort RENTAL_DATES_SORT =
      Sort.by(Order.desc("rentedFrom"), Order.desc("rentedTo"));

  private final RentalRepository rentalRepository;

  private final CarRepository carRepository;

  private final UserRepository userRepository;

  private final MappingService mappingService;

  @Override
  public RentalResponse create(RentalCreateRequest model, String renterId) {
    if (model.getRentedTo().isBefore(model.getRentedFrom())) {
      throw new InvalidDateRangeException();
    }

    var rentalDates = getRentalDates(model.getCarId());
    if (rentalDates.contains(model.getRentedFrom()) || rentalDates.contains(model.getRentedTo())) {
      throw new CarNotAvailableInProvidedDateRangeException();
    }

    var car =
        carRepository
            .findById(model.getCarId())
            .orElseThrow(
                () -> new InvalidCarException(String.format(INVALID_CAR, model.getCarId())));

    if (car.getOwner().getId().equals(renterId)) {
      throw new RentOwnedCarException();
    }

    var rentalCandidate =
        rentalRepository.findFirstByStatusAndCarIdAndRenterId(
            RentalStatus.PENDING_VERIFICATION, model.getCarId(), renterId);
    if (rentalCandidate.isPresent()) {
      throw new ExistingPendingVerificationRentalException();
    }

    var renter =
        userRepository
            .findById(renterId)
            .orElseThrow(() -> new InvalidUserException(String.format(INVALID_USER, renterId)));

    var rental = mappingService.toRental(model);
    rental.setStatus(RentalStatus.PENDING_VERIFICATION);
    rental.setCar(car);
    rental.setRenter(renter);
    rental.setPricePerDay(car.getPricePerDay());

    var days = DAYS.between(rental.getRentedFrom(), rental.getRentedTo()) + 1;
    rental.setDays(days);

    rental.setTotalPrice(rental.getPricePerDay().multiply(BigDecimal.valueOf(days)));

    return mappingService.toRentalResponse(rentalRepository.save(rental));
  }

  @Override
  public Page<RentalResponse> findAll(FindAllRentalsRequest model, String userId) {
    var predicate = getBaseRentalsPredicate(model, userId);

    if (model.getCarId() != null) {
      predicate = predicate.and(RENTAL_PATH.car.id.eq(model.getCarId()));
    }

    var statusPredicate =
        predicate.and(RENTAL_PATH.status.in(RentalStatus.STARTED, RentalStatus.FINISHED));
    if (model.getIsRentalRequest() != null && model.getIsRentalRequest()) {
      statusPredicate = statusPredicate.not();
    }

    predicate = predicate.and(statusPredicate);

    if (model.getRentalId() != null) {
      predicate = predicate.and(RENTAL_PATH.id.eq(model.getRentalId()));
    }

    if (model.getStatus() != null) {
      predicate = predicate.and(RENTAL_PATH.status.eq(model.getStatus()));
    }

    return rentalRepository
        .findAll(predicate, PageRequest.of(model.getPage() - 1, 6, RENTAL_DATES_SORT))
        .map(mappingService::toRentalResponse);
  }

  private BooleanExpression getBaseRentalsPredicate(RentalsSearch search, String userId) {
    var predicate = RENTAL_PATH.car.owner.id.eq(userId).or(RENTAL_PATH.renter.id.eq(userId));

    if (search.getRenterId() != null) {
      predicate = predicate.and(RENTAL_PATH.renter.id.eq(search.getRenterId()));
    }

    if (search.getOwnerId() != null) {
      predicate = predicate.and(RENTAL_PATH.car.owner.id.eq(search.getOwnerId()));
    }

    return predicate;
  }

  @Override
  public RentalsCountResponse getCount(RentalsCountRequest model, String userId) {
    var predicate = getBaseRentalsPredicate(model, userId);

    var rentalRequestsCount =
        rentalRepository.count(
            predicate.and(
                RENTAL_PATH.status.in(
                    RentalStatus.PENDING_VERIFICATION,
                    RentalStatus.APPROVED,
                    RentalStatus.REJECTED)));

    var rentalsCount =
        rentalRepository.count(
            predicate.and(RENTAL_PATH.status.in(RentalStatus.STARTED, RentalStatus.FINISHED)));

    return new RentalsCountResponse(rentalRequestsCount, rentalsCount);
  }

  @Override
  public void delete(String rentalId, String renterId) {
    var rental =
        rentalRepository
            .findFirstByIdAndRenterId(rentalId, renterId)
            .orElseThrow(
                () ->
                    new InvalidRentalException(
                        String.format(INVALID_RENTAL_RENTER, rentalId, renterId)));

    throwIfNotInPendingVerificationStatus(rental);

    rentalRepository.delete(rental);
  }

  @Override
  public void reject(String rentalId, String ownerId) {
    var rental =
        rentalRepository
            .findFirstByIdAndCarOwnerId(rentalId, ownerId)
            .orElseThrow(
                () ->
                    new InvalidRentalException(
                        String.format(INVALID_RENTAL_OWNER, rentalId, ownerId)));

    throwIfNotInPendingVerificationStatus(rental);

    rental.setStatus(RentalStatus.REJECTED);

    rentalRepository.save(rental);
  }

  @Override
  public void approve(String rentalId, String ownerId) {
    var rental =
        rentalRepository
            .findFirstByIdAndCarOwnerId(rentalId, ownerId)
            .orElseThrow(
                () ->
                    new InvalidRentalException(
                        String.format(INVALID_RENTAL_OWNER, rentalId, ownerId)));

    throwIfNotInPendingVerificationStatus(rental);

    rental.setStatus(RentalStatus.APPROVED);

    if (rental.getRentedFrom().isEqual(LocalDate.now())
        || rental.getRentedFrom().isBefore(LocalDate.now())) {
      rental.setStatus(RentalStatus.STARTED);
    }

    rentalRepository.save(rental);
  }

  @PostConstruct
  @Scheduled(cron = "0 0 5 * * *")
  @Override
  public void updateOngoingRentals() {
    var startedRentals =
        rentalRepository.findAllByStatusAndRentedFrom(RentalStatus.APPROVED, LocalDate.now());
    startedRentals.forEach(r -> r.setStatus(RentalStatus.STARTED));

    rentalRepository.saveAll(startedRentals);

    var finishedRentals =
        rentalRepository.findAllByStatusAndRentedTo(
            RentalStatus.STARTED, LocalDate.now().minusDays(1));
    finishedRentals.forEach(r -> r.setStatus(RentalStatus.FINISHED));

    rentalRepository.saveAll(finishedRentals);
  }

  @Override
  public Set<LocalDate> getRentalDates(String carId) {
    var predicate =
        (RENTAL_PATH.car.id.eq(carId))
            .and(
                (RENTAL_PATH.rentedFrom.goe(LocalDate.now()))
                    .or(RENTAL_PATH.rentedTo.goe(LocalDate.now())))
            .and(RENTAL_PATH.status.in(RentalStatus.APPROVED, RentalStatus.STARTED));

    var rentals = rentalRepository.findAll(predicate, RENTAL_DATES_SORT);

    var result = new TreeSet<>(LocalDate::compareTo);
    for (Rental rental : rentals) {
      result.addAll(
          LongStream.rangeClosed(0, rental.getDays())
              .mapToObj(rental.getRentedFrom()::plusDays)
              .collect(Collectors.toSet()));
    }

    // 16 - 20
    // 12-16
    //

    return result;
  }

  private void throwIfNotInPendingVerificationStatus(Rental rental) {
    if (rental.getStatus() != RentalStatus.PENDING_VERIFICATION) {
      throw new RentalNotInPendingVerificationStatusException();
    }
  }
}
