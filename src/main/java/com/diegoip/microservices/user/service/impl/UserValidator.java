package com.diegoip.microservices.user.service.impl;


import com.diegoip.microservices.user.configuration.UserConfig;
import com.diegoip.microservices.user.model.UserRequest;
import com.diegoip.microservices.user.validator.RegexRule;
import com.diegoip.microservices.user.validator.Rule;
import com.diegoip.microservices.user.validator.RuleResult;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Pattern;

@Component
public class  UserValidator implements Rule<UserRequest> {

  private static final String EMAIL = "email";
  private static final String PASSWORD = "password";
  private final Map<String, ? extends RegexRule> rulesMap ;

  public UserValidator(UserConfig userConfig) {

    RegexRule ruleEmail = RegexRule.builder()
        .pattern(Pattern.compile(userConfig.getEmailRegex()))
        .errorMessage(userConfig.getEmailDescription())
        .build();

    RegexRule rulePassword = RegexRule.builder()
        .pattern(Pattern.compile(userConfig.getPasswordRegex()))
        .errorMessage(userConfig.getPasswordDescription())
        .build();

    rulesMap = Map.of(EMAIL, ruleEmail, PASSWORD, rulePassword);

  }


  @Override
  public RuleResult validate(UserRequest request) {

    RuleResult result = new RuleResult();

    RuleResult ruleResultEmail = rulesMap.get(EMAIL).validate(request.getEmail());
    result.getErrors().addAll(ruleResultEmail.getErrors());

    RuleResult ruleResultPassword = rulesMap.get(PASSWORD).validate(request.getPassword());
    result.getErrors().addAll(ruleResultPassword.getErrors());

    return result;
  }
}
