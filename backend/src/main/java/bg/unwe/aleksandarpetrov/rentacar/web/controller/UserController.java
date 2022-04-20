package bg.unwe.aleksandarpetrov.rentacar.web.controller;

import bg.unwe.aleksandarpetrov.rentacar.entity.User;
import bg.unwe.aleksandarpetrov.rentacar.service.UserService;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.UserInfoResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.UserRegisterRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/any")
  public boolean userExists(@AuthenticationPrincipal User user) {
    return userService.userExists();
  }

  @PostMapping
  public UserInfoResponse register(@RequestBody @Valid @NotNull UserRegisterRequest model) {
    return userService.register(model);
  }
}