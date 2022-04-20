package bg.unwe.aleksandarpetrov.rentacar.service;


import bg.unwe.aleksandarpetrov.rentacar.entity.User;

public interface JwtService {

    String generateToken(User user, String secretKey);
}
