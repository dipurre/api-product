package com.diegoip.microservices.user.validator;

import lombok.Builder;
import lombok.Getter;

import java.util.regex.Pattern;

@Builder
@Getter
public class RegexRule implements Rule<String> {

  private final String errorMessage;
  private final Pattern pattern;

  @Override
  public RuleResult validate(String data) {
    RuleResult ruleResult = new RuleResult();
    if (!pattern.matcher(data).find()) {
      ruleResult.addError(errorMessage);
    }
    return ruleResult;
  }
}
