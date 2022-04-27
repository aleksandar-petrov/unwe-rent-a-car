package bg.unwe.aleksandarpetrov.rentacar.service;

import bg.unwe.aleksandarpetrov.rentacar.entity.Car;
import bg.unwe.aleksandarpetrov.rentacar.entity.Photo;
import bg.unwe.aleksandarpetrov.rentacar.entity.User;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarCreateRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.car.CarResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.photo.PhotoPatchRequest;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.UserInfoResponse;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.UserRegisterRequest;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MappingService {

  User toUser(UserRegisterRequest model);

  UserInfoResponse toUserInfoResponse(User user);

  Car toCar(CarCreateRequest model);

  CarResponse toCarResponse(Car car);

  void update(PhotoPatchRequest model, @MappingTarget Photo photo);

  List<CarResponse> toCarResponseList(List<Car> carList);
}
