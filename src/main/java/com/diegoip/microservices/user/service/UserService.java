package com.diegoip.microservices.user.service;

import com.diegoip.microservices.user.model.LoginRequest;
import com.diegoip.microservices.user.model.UserRequest;
import com.diegoip.microservices.user.model.UserResponse;

public interface UserService {

  UserResponse register(UserRequest userRequest);

  UserResponse login(LoginRequest loginRequest);

}
