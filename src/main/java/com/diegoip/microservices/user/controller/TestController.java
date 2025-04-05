package com.diegoip.microservices.user.controller;

import com.diegoip.microservices.user.controller.openapi.TestControllerOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController implements TestControllerOpenApi {

  @Override
  @GetMapping
  public String test() {
    return "test";
  }
}
