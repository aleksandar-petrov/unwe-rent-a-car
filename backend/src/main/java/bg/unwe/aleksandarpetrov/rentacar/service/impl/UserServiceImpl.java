package bg.unwe.aleksandarpetrov.rentacar.service.impl;

import static bg.unwe.aleksandarpetrov.rentacar.constant.AuthConstants.Role.ROLE_ADMIN;
import static bg.unwe.aleksandarpetrov.rentacar.constant.AuthConstants.Role.ROLE_MODERATOR;
import static bg.unwe.aleksandarpetrov.rentacar.constant.AuthConstants.Role.ROLE_USER;
import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.DUPLICATE_EMAIL;
import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.DUPLICATE_PHONE_NUMBER;
import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.PASSWORD_MISMATCH;
import static bg.unwe.aleksandarpetrov.rentacar.constant.ErrorConstants.USER_NOT_FOUND;

import bg.unwe.aleksandarpetrov.rentacar.entity.QUser;
import bg.unwe.aleksandarpetrov.rentacar.entity.Role;
import bg.unwe.aleksandarpetrov.rentacar.entity.User;
import bg.unwe.aleksandarpetrov.rentacar.exception.DuplicateEmailException;
import bg.unwe.aleksandarpetrov.rentacar.exception.DuplicatePhoneNumberException;
import bg.unwe.aleksandarpetrov.rentacar.exception.PasswordMismatchException;
import bg.unwe.aleksandarpetrov.rentacar.repository.RoleRepository;
import bg.unwe.aleksandarpetrov.rentacar.repository.UserRepository;
import bg.unwe.aleksandarpetrov.rentacar.service.MappingService;
import bg.unwe.aleksandarpetrov.rentacar.service.UserService;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.AnyUserExistsRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.UserInfoResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.UserRegisterRequest;
import com.querydsl.core.types.dsl.Expressions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
  }

  @Override
  public UserInfoResponse register(UserRegisterRequest model) {
    if (!model.getPassword().equals(model.getConfirmPassword())) {
      throw new PasswordMismatchException(PASSWORD_MISMATCH);
    }

    if (userRepository.findFirstByEmail(model.getEmail()).isPresent()) {
      throw new DuplicateEmailException(String.format(DUPLICATE_EMAIL, model.getEmail()));
    }

    if (userExists(AnyUserExistsRequest.builder().phoneNumber(model.getPhoneNumber()).build())) {
      throw new DuplicatePhoneNumberException(
          String.format(DUPLICATE_PHONE_NUMBER, model.getPhoneNumber()));
    }

    var user = mappingService.toUser(model);

    user.setPassword(passwordEncoder.encode(model.getPassword()));
    setUserRoles(user);

    userRepository.save(user);

    return mappingService.toUserInfoResponse(user);
  }

  @Override
  public boolean userExists(AnyUserExistsRequest model) {
    if (Objects.equals(model, new AnyUserExistsRequest())) {
      return userExists();
    }

    var userPath = QUser.user;

    var predicate = Expressions.TRUE.isTrue();
    if (model.getEmail() != null) {
      predicate = predicate.and(userPath.email.eq(model.getEmail()));
    }
    if (model.getPhoneNumber() != null) {
      if (!model.getPhoneNumber().matches("^(([+]359)|0)8[789]\\d{7}$")) {
        predicate = predicate.and(Expressions.TRUE.isFalse());
      } else {
        var phoneNumberPredicate =
            userPath
                .phoneNumber
                .eq(model.getPhoneNumber())
                .or(userPath.phoneNumber.eq(getOtherPhoneNumberVariant(model.getPhoneNumber())));
        predicate = predicate.and(phoneNumberPredicate);
      }
    }

    return userRepository.findOne(predicate).isPresent();
  }

  private String getOtherPhoneNumberVariant(String phoneNumber) {
    if (phoneNumber.startsWith("+")) {
      return phoneNumber.replace("+359", "0");
    }

    return phoneNumber.replace("0", "+359");
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
