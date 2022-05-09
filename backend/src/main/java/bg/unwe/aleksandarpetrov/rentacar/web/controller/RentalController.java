package bg.unwe.aleksandarpetrov.rentacar.web.controller;

import bg.unwe.aleksandarpetrov.rentacar.entity.User;
import bg.unwe.aleksandarpetrov.rentacar.service.RentalService;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.FindAllRentalsRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalCreateRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rentals")
public class RentalController {

  private final RentalService rentalService;

  @PostMapping
  public RentalResponse create(
      @RequestBody @NotNull @Valid RentalCreateRequest model, @AuthenticationPrincipal User user) {
    return rentalService.create(model, user.getId());
  }

  @GetMapping
  public Page<RentalResponse> getAll(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false) String renterId,
      @RequestParam(required = false) String ownerId,
      @RequestParam(required = false) String carId,
      @RequestParam(required = false) boolean isRentalRequest,
      @AuthenticationPrincipal User user) {
    return rentalService.findAll(
        new FindAllRentalsRequest(page, renterId, ownerId, carId, isRentalRequest), user.getId());
  }
}
