package com.diegoip.microservices.user.service.impl;

import com.diegoip.microservices.user.configuration.UserConfig;
import com.diegoip.microservices.user.model.UserRequest;
import com.diegoip.microservices.user.util.TestUtil;
import com.diegoip.microservices.user.validator.RuleResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidatorTest {

  private UserValidator userValidator;

  @BeforeEach
  public void setUp() {
    UserConfig userConfig = TestUtil.parseJsonToObject("userConfig.json", UserConfig.class);

    userValidator = new UserValidator(userConfig);
  }

  @Test
  void validateUser() {
    UserRequest userRequest = TestUtil.parseJsonToObject("userRequestValid.json", UserRequest.class);

    RuleResult ruleResult = userValidator.validate(userRequest);

    assertTrue(ruleResult.getErrors().isEmpty());

  }
}
