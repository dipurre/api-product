package com.diegoip.microservices.user.service.impl;

import com.diegoip.microservices.user.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

  private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode("confe256confe256confe256confe256confe256confe256"));
  private static final long expirationTime = 1000 * 60 * 60 * 10;
  static final String PREFIX = "Bearer";

  @Override
  public String generateToken(String email) {

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expirationTime);

    return Jwts.builder()
        .subject(email)
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(SECRET_KEY)
        .compact();
  }

  @Override
  public String getAuthUser(HttpServletRequest request) {

    String token = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (token != null) {
      return Jwts.parser()
          .verifyWith(SECRET_KEY)
          .build()
          .parseSignedClaims(token.replace(PREFIX, "").trim())
          .getPayload()
          .getSubject();
    }

    return null;
  }
}
