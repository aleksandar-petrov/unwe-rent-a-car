package bg.unwe.aleksandarpetrov.rentacar.web.filter;

import static bg.unwe.aleksandarpetrov.rentacar.constant.AuthConstants.JWT.HEADER_AUTHORIZATION;
import static bg.unwe.aleksandarpetrov.rentacar.constant.AuthConstants.JWT.TOKEN_PREFIX;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  private final String secretKey;

  private final UserDetailsService userDetailsService;

  public JWTAuthorizationFilter(
      AuthenticationManager authenticationManager,
      String secretKey,
      UserDetailsService userDetailsService) {
    super(authenticationManager);
    this.secretKey = secretKey;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    var header = req.getHeader(HEADER_AUTHORIZATION);

    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    var authentication = getAuthentication(req);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    var token = request.getHeader(HEADER_AUTHORIZATION);
    if (token != null) {
      String user =
          JWT.require(Algorithm.HMAC512(secretKey.getBytes()))
              .build()
              .verify(token.replace(TOKEN_PREFIX, ""))
              .getSubject();

      if (user != null) {
        var entity = userDetailsService.loadUserByUsername(user);
        return new UsernamePasswordAuthenticationToken(entity, null, entity.getAuthorities());
      }

      return null;
    }

    return null;
  }
}
