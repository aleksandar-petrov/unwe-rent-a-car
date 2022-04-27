package bg.unwe.aleksandarpetrov.rentacar.web.controller;

import bg.unwe.aleksandarpetrov.rentacar.entity.User;
import bg.unwe.aleksandarpetrov.rentacar.service.CarService;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarCreateRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {

  private final CarService carService;

  @PostMapping
  public CarResponse create(
      @RequestBody @Valid @NotNull CarCreateRequest model, @AuthenticationPrincipal User user) {
    return carService.create(model, user.getId());
  }

  @GetMapping
  public Page<CarResponse> findAllByOwnerId(
      @RequestParam("ownerId") String ownerId,
      @RequestParam(required = false, defaultValue = "1") int page) {
    return carService.findAllByOwnerId(ownerId, page);
  }

  @GetMapping("/{id}")
  public CarResponse findById(@PathVariable String id) {
    return carService.findById(id);
  }
}
