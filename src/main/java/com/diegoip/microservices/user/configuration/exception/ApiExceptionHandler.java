package com.diegoip.microservices.user.configuration.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<?> handleGlobalException(ApiException ex, WebRequest request) {
    Problem problem = createProblemBuilder(ex.getMessage(), ex.getDetails())
        .build();

    return handleExceptionInternal(ex, problem, new HttpHeaders(), ex.getHttpStatus(), request);

  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    Problem problem = createProblemBuilder(ex.getMessage(), null)
        .build();

    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {

    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    Problem problem = createProblemBuilder(ex.getMessage(), null)
        .build();

    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

  }


  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

    return super.handleExceptionInternal(ex, body, headers, statusCode, request);
  }

  private Problem.ProblemBuilder createProblemBuilder(String message, List<String> details) {
    return Problem.builder()
        .message(message)
        .details(details);
  }
}
