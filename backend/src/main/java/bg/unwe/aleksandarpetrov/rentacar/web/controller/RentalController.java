package bg.unwe.aleksandarpetrov.rentacar.web.controller;

import bg.unwe.aleksandarpetrov.rentacar.entity.User;
import bg.unwe.aleksandarpetrov.rentacar.service.RentalService;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.FindAllRentalsRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalCreateRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalsCountRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalsCountResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.rental.RentalsFinancialStatsResponse;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
      FindAllRentalsRequest request, @AuthenticationPrincipal User user) {
    return rentalService.findAll(request, user.getId());
  }

  @GetMapping("/count")
  public RentalsCountResponse getCount(
      @RequestParam(required = false) String renterId,
      @RequestParam(required = false) String ownerId,
      @AuthenticationPrincipal User user) {
    return rentalService.getCount(new RentalsCountRequest(renterId, ownerId), user.getId());
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id, @AuthenticationPrincipal User user) {
    rentalService.delete(id, user.getId());
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PostMapping("/{id}/reject")
  public void reject(@PathVariable String id, @AuthenticationPrincipal User user) {
    rentalService.reject(id, user.getId());
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PostMapping("/{id}/approve")
  public void approve(@PathVariable String id, @AuthenticationPrincipal User user) {
    rentalService.approve(id, user.getId());
  }

  @GetMapping("/rental-dates")
  public Set<LocalDate> getRentalDates(@RequestParam String carId) {
    return rentalService.getRentalDates(carId);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/financial-stats")
  public RentalsFinancialStatsResponse getFinancialStats() {
    return rentalService.getFinancialStats();
  }
}
