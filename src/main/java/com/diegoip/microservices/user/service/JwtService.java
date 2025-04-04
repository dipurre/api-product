package com.diegoip.microservices.user.service;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {

  String generateToken(String email);

  String getAuthUser(HttpServletRequest request);

}
