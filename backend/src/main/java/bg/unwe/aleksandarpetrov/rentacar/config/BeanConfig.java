package bg.unwe.aleksandarpetrov.rentacar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class BeanConfig {

  private static final AwsCredentialsProvider AWS_CREDENTIALS_PROVIDER =
      StaticCredentialsProvider.create(
          AwsBasicCredentials.create(
              "AKIA6CUNTCYBTS635MIB", "+J10DiQg4gSZmoIeu3kAix5FjZmaqqcb2TFp/Age"));

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public S3Presigner s3Presigner() {
    return S3Presigner.builder()
        .credentialsProvider(AWS_CREDENTIALS_PROVIDER)
        .region(Region.EU_WEST_2)
        .build();
  }

  @Bean
  public S3AsyncClient s3AsyncClient() {
    return S3AsyncClient.builder()
        .credentialsProvider(AWS_CREDENTIALS_PROVIDER)
        .region(Region.EU_WEST_2)
        .build();
  }
}
