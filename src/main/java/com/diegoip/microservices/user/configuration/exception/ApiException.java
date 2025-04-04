package com.diegoip.microservices.user.configuration.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
public class ApiException extends RuntimeException {

  private String message;
  private HttpStatus httpStatus;
  private List<String> details;

}
