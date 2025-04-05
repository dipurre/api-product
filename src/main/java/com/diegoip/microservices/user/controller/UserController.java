package com.diegoip.microservices.user.controller;

import com.diegoip.microservices.user.controller.openapi.UserControllerOpenApi;
import com.diegoip.microservices.user.model.LoginRequest;
import com.diegoip.microservices.user.model.UserRequest;
import com.diegoip.microservices.user.model.UserResponse;
import com.diegoip.microservices.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements UserControllerOpenApi {

  private final UserService userService;

  @Override
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse register(@RequestBody UserRequest userRequest) {
    return userService.register(userRequest);
  }

  @Override
  @PostMapping("/login")
  public UserResponse login(@RequestBody LoginRequest loginRequest) {
    return userService.login(loginRequest);
  }

}
