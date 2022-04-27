package bg.unwe.aleksandarpetrov.rentacar.web.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import bg.unwe.aleksandarpetrov.rentacar.entity.User;
import bg.unwe.aleksandarpetrov.rentacar.exception.AuthException;
import bg.unwe.aleksandarpetrov.rentacar.service.JwtService;
import bg.unwe.aleksandarpetrov.rentacar.web.payload.user.UserLoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final ObjectMapper objectMapper;

  private final AuthenticationManager authenticationManager;

  private final String secretKey;

  private final UserDetailsService userDetailsService;

  private final JwtService jwtService;

  public JWTAuthenticationFilter(
      ObjectMapper objectMapper,
      AuthenticationManager authenticationManager,
      String secretKey,
      UserDetailsService userDetailsService,
      JwtService jwtService) {
    this.objectMapper = objectMapper;
    this.authenticationManager = authenticationManager;
    this.secretKey = secretKey;
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException {
    try {
      var inputCredentials =
          this.objectMapper.readValue(req.getInputStream(), UserLoginRequest.class);

      var user = this.userDetailsService.loadUserByUsername(inputCredentials.getEmail());

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              user, inputCredentials.getPassword(), user.getAuthorities()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (AuthenticationException e) {
      throw new AuthException(e.getMessage());
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
      throws IOException {
    var userEntity = ((User) auth.getPrincipal());

    var token = jwtService.generateToken(userEntity, secretKey);

    res.setContentType(APPLICATION_JSON_VALUE);
    res.setCharacterEncoding("UTF-8");
    res.getWriter().write(objectMapper.writeValueAsString(Map.of("access_token", token)));
  }
}
