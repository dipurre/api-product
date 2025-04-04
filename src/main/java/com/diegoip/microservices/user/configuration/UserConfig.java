package com.diegoip.microservices.user.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class UserConfig {

  @Value("${user.validators.email.regex}")
  private String emailRegex;

  @Value("${user.validators.email.description}")
  private String emailDescription;

  @Value("${user.validators.password.regex}")
  private String passwordRegex;

  @Value("${user.validators.password.description}")
  private String passwordDescription;

}
