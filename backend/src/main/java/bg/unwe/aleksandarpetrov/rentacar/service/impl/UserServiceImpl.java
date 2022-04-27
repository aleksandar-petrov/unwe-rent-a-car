package bg.unwe.aleksandarpetrov.rentacar.service.impl;

import static bg.unwe.aleksandarpetrov.rentacar.constant.AuthConstants.Role.ROLE_ADMIN;
import static bg.unwe.aleksandarpetrov.rentacar.constant.AuthConstants.Role.ROLE_MODERATOR;
import static bg.unwe.aleksandarpetrov.rentacar.constant.AuthConstants.Role.ROLE_USER;
import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.DUPLICATE_EMAIL;
import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.PASSWORD_MISMATCH;

import bg.unwe.aleksandarpetrov.rentacar.entity.Role;
import bg.unwe.aleksandarpetrov.rentacar.entity.User;
import bg.unwe.aleksandarpetrov.rentacar.exception.DuplicateEmailException;
import bg.unwe.aleksandarpetrov.rentacar.exception.PasswordMismatchException;
import bg.unwe.aleksandarpetrov.rentacar.repository.RoleRepository;
import bg.unwe.aleksandarpetrov.rentacar.repository.UserRepository;
import bg.unwe.aleksandarpetrov.rentacar.service.MappingService;
import bg.unwe.aleksandarpetrov.rentacar.service.UserService;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.UserInfoResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.UserRegisterRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final MappingService mappingService;

  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository
        .findFirstByEmail(email)
        .orElseThrow(
            () ->
                new UsernameNotFoundException(
                    String.format("User with email %s not found.", email)));
  }

  @Override
  public UserInfoResponse register(UserRegisterRequest model) {

    if (userRepository.findFirstByEmail(model.getEmail()).isPresent()) {
      throw new DuplicateEmailException(String.format(DUPLICATE_EMAIL, model.getEmail()));
    }

    if (!model.getPassword().equals(model.getConfirmPassword())) {
      throw new PasswordMismatchException(PASSWORD_MISMATCH);
    }

    var user = mappingService.toUser(model);

    user.setPassword(passwordEncoder.encode(model.getPassword()));
    setUserRoles(user);

    userRepository.save(user);

    return mappingService.toUserInfoResponse(user);
  }

  @Override
  public boolean userExists(String email) {
    if (email != null) {
      return userRepository.findFirstByEmail(email).isPresent();
    }

    return userExists();
  }

  private boolean userExists() {
    return userRepository.count() > 0;
  }

  private void setUserRoles(User user) {
    var userRoles = new ArrayList<>(Collections.singleton(ROLE_USER));

    var userExists = userExists();
    if (!userExists) {
      userRoles.addAll(List.of(ROLE_ADMIN, ROLE_MODERATOR));
    }

    user.getRoles().addAll(roleRepository.findAllByNameIn(userRoles));
  }

  @PostConstruct
  public void initRoles() {
    if (roleRepository.count() > 0) {
      return;
    }

    roleRepository.saveAll(
        List.of(new Role(ROLE_ADMIN), new Role(ROLE_MODERATOR), new Role(ROLE_USER)));
  }
}
