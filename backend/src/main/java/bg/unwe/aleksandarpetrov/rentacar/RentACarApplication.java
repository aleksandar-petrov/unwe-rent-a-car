package bg.unwe.aleksandarpetrov.rentacar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RentACarApplication {

  public static void main(String[] args) {
    SpringApplication.run(RentACarApplication.class, args);
  }
}
