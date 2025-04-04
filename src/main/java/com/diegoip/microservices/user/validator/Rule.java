package com.diegoip.microservices.user.validator;

public interface Rule<T> {
  RuleResult validate(T t);
}
