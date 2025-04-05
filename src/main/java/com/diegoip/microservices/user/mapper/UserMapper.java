package com.diegoip.microservices.user.mapper;

import com.diegoip.microservices.user.entity.UserEntity;
import com.diegoip.microservices.user.entity.UserPhoneEntity;
import com.diegoip.microservices.user.model.LoginRequest;
import com.diegoip.microservices.user.model.UserRequest;
import com.diegoip.microservices.user.model.UserResponse;
import com.diegoip.microservices.user.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final JwtService tokenService;
  private final PasswordEncoder passwordEncoder;

  public UserEntity toDomainObject(UserRequest userRequest) {

    UserEntity user = UserEntity.builder()
        .id(UUID.randomUUID().toString())
        .name(userRequest.getName())
        .password(passwordEncoder.encode(userRequest.getPassword()))
        .email(userRequest.getEmail())
        .created(LocalDateTime.now())
        .modified(LocalDateTime.now())
        .isActive(true)
        .lastLogin(LocalDateTime.now())
        .token(tokenService.generateToken(userRequest.getEmail()))
        .build();

    Optional.ofNullable(userRequest.getPhones())
        .orElseGet(Collections::emptyList)
        .stream()
        .map(phoneRequest -> UserPhoneEntity.builder()
            .phoneNumber(phoneRequest.getNumber())
            .cityCode(phoneRequest.getCityCode())
            .countryCode(phoneRequest.getCountryCode())
            .build())
        .forEach(user::addPhone);
    return user;
  }

  public UserResponse toModelObject(UserEntity userEntity) {
    return UserResponse.builder()
        .id(userEntity.getId())
        .token(userEntity.getToken())
        .lastLogin(userEntity.getLastLogin())
        .created(userEntity.getCreated())
        .modified(userEntity.getModified())
        .isActive(userEntity.getIsActive())
        .build();
  }

  public void copyToDomainObject(LoginRequest loginRequest, UserEntity userEntity) {
    userEntity.setToken(tokenService.generateToken(loginRequest.getEmail()));
    userEntity.setLastLogin(LocalDateTime.now());
    userEntity.setModified(LocalDateTime.now());
  }
}
