package com.diegoip.microservices.user.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JwtServiceImplTest {



  @Test
  void generateTokenWhenIsValid() {
    JwtServiceImpl jwtService = new JwtServiceImpl();

    String token = jwtService.generateToken("email");

    assertNotNull(token);
  }
}
