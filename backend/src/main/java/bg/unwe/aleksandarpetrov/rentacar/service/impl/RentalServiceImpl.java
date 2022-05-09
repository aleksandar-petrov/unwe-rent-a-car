package bg.unwe.aleksandarpetrov.rentacar.service.impl;

import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.INVALID_CAR;
import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.INVALID_USER;
import static java.time.temporal.ChronoUnit.DAYS;

import bg.unwe.aleksandarpetrov.rentacar.entity.QRental;
import bg.unwe.aleksandarpetrov.rentacar.entity.enumeration.RentalStatus;
import bg.unwe.aleksandarpetrov.rentacar.exception.ExistingPendingVerificationRentalException;
import bg.unwe.aleksandarpetrov.rentacar.exception.InvalidCarException;
import bg.unwe.aleksandarpetrov.rentacar.exception.InvalidDateRangeException;
import bg.unwe.aleksandarpetrov.rentacar.exception.InvalidUserException;
import bg.unwe.aleksandarpetrov.rentacar.exception.RentOwnedCarException;
import bg.unwe.aleksandarpetrov.rentacar.repository.CarRepository;
import bg.unwe.aleksandarpetrov.rentacar.repository.RentalRepository;
import bg.unwe.aleksandarpetrov.rentacar.repository.UserRepository;
import bg.unwe.aleksandarpetrov.rentacar.service.MappingService;
import bg.unwe.aleksandarpetrov.rentacar.service.RentalService;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.FindAllRentalsRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalCreateRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalResponse;
import com.querydsl.core.types.dsl.Expressions;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RentalServiceImpl implements RentalService {

  private final RentalRepository rentalRepository;

  private final CarRepository carRepository;

  private final UserRepository userRepository;

  private final MappingService mappingService;

  @Override
  public RentalResponse create(RentalCreateRequest model, String renterId) {
    if (model.getRentedTo().isBefore(model.getRentedFrom())) {
      throw new InvalidDateRangeException();
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
    var rentalPath = QRental.rental;

    var predicate = Expressions.TRUE.isTrue();

    if (model.getOwnerId() != null) {
      predicate = predicate.and(rentalPath.car.owner.id.eq(model.getOwnerId()));
    }

    if (model.getRenterId() != null) {
      predicate = predicate.and(rentalPath.renter.id.eq(model.getRenterId()));
    }

    if (model.getCarId() != null) {
      predicate = predicate.and(rentalPath.car.id.eq(model.getCarId()));
    }

    if (model.isRentalRequest()) {
      predicate = predicate.and(rentalPath.status.in(RentalStatus.PENDING_VERIFICATION, RentalStatus.REJECTED));
    }

    predicate = predicate.and(rentalPath.car.owner.id.eq(userId).or(rentalPath.renter.id.eq(userId)));

    return rentalRepository
        .findAll(predicate, PageRequest.of(model.getPage() - 1, 6))
        .map(mappingService::toRentalResponse);
  }
}
