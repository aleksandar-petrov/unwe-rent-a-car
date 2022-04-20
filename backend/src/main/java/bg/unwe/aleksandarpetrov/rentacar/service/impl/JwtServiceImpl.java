package bg.unwe.aleksandarpetrov.rentacar.service.impl;

import static bg.unwe.aleksandarpetrov.rentacar.constant.AuthConstants.JWT.EXPIRATION_TIME;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import bg.unwe.aleksandarpetrov.rentacar.entity.User;
import bg.unwe.aleksandarpetrov.rentacar.service.JwtService;
import com.auth0.jwt.JWT;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

  @Override
  public String generateToken(User user, String secretKey) {
    return JWT.create()
        .withSubject(user.getEmail())
        .withClaim(
            "roles",
            user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")))
        .withClaim("userId", user.getId())
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .sign(HMAC512(secretKey.getBytes()));
  }
}
