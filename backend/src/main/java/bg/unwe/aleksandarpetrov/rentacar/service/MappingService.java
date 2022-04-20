package bg.unwe.aleksandarpetrov.rentacar.service;

import bg.unwe.aleksandarpetrov.rentacar.entity.User;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.UserInfoResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.UserRegisterRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MappingService {

  User toUser(UserRegisterRequest model);

  UserInfoResponse toUserInfoResponse(User user);
}
