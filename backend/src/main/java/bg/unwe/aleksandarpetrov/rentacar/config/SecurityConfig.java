package bg.unwe.aleksandarpetrov.rentacar.config;

import bg.unwe.aleksandarpetrov.rentacar.service.JwtService;
import bg.unwe.aleksandarpetrov.rentacar.service.UserService;
import bg.unwe.aleksandarpetrov.rentacar.web.filter.JWTAuthenticationFilter;
import bg.unwe.aleksandarpetrov.rentacar.web.filter.JWTAuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserService userDetailsService;

  private final PasswordEncoder passwordEncoder;

  private final String secretKey;

  private final ObjectMapper objectMapper;

  private final JwtService jwtService;

  public SecurityConfig(
      UserService userDetailsService,
      PasswordEncoder passwordEncoder,
      @Value("${jwt.secret.key}") String secretKey,
      ObjectMapper objectMapper,
      JwtService jwtService) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.secretKey = secretKey;
    this.objectMapper = objectMapper;
    this.jwtService = jwtService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(HttpMethod.GET, "/users/any")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/users")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf()
        .disable()
        .cors()
        .disable()
        .httpBasic()
        .disable()
        .formLogin()
        .disable()
        .logout()
        .disable()
        .addFilter(
            new JWTAuthenticationFilter(
                objectMapper, authenticationManager(), secretKey, userDetailsService, jwtService))
        .addFilter(
            new JWTAuthorizationFilter(authenticationManager(), secretKey, userDetailsService))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    var source = new UrlBasedCorsConfigurationSource();
    var corsConfig = new CorsConfiguration().applyPermitDefaultValues();
    corsConfig.addAllowedMethod("*");

    source.registerCorsConfiguration("/**", corsConfig);

    return source;
  }
}
