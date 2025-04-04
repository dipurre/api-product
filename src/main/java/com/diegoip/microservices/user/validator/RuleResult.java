package com.diegoip.microservices.user.validator;

import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RuleResult {
  private final List<String> errors;

  public Boolean isValid() {
    return CollectionUtils.isEmpty(this.errors);
  }

  public void addError(String error) {
    this.errors.add(error);
  }

  public RuleResult() {
    this.errors = new ArrayList<>();
  }
}
