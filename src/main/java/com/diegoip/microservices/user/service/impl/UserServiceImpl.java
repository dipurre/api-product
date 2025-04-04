package com.diegoip.microservices.user.service.impl;

import com.diegoip.microservices.user.entity.UserEntity;
import com.diegoip.microservices.user.configuration.exception.ApiException;
import com.diegoip.microservices.user.mapper.UserMapper;
import com.diegoip.microservices.user.model.LoginRequest;
import com.diegoip.microservices.user.model.UserRequest;
import com.diegoip.microservices.user.model.UserResponse;
import com.diegoip.microservices.user.repository.UserRepository;
import com.diegoip.microservices.user.service.UserService;
import com.diegoip.microservices.user.validator.RuleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final UserValidator userValidator;
  private final PasswordEncoder passwordEncoder;


  @Override
  public UserResponse register(UserRequest userRequest) {

    RuleResult ruleResult = userValidator.validate(userRequest);

    if (!ruleResult.isValid()) {
      throw ApiException.builder()
          .message("datos invalidos")
          .details(ruleResult.getErrors())
          .httpStatus(HttpStatus.CONFLICT)
          .build();
    }

    if (userRepository.findByEmail(userRequest.getEmail())
        .isPresent()) {
      throw ApiException.builder()
          .message("El correo ya registrado")
          .httpStatus(HttpStatus.CONFLICT)
          .build();
    }

    return Optional.of(userRequest)
        .map(userMapper::toDomainObject)
        .map(userRepository::save)
        .map(userMapper::toModelObject)
        .orElseThrow();
  }

  @Override
  public UserResponse login(LoginRequest loginRequest) {
    UserEntity userFound = userRepository
        .findByEmail(loginRequest.getEmail())
        .orElseThrow(() -> ApiException.builder()
            .message(format("el email %s no existe", loginRequest.getEmail()))
            .httpStatus(HttpStatus.NOT_FOUND)
            .build());

    if (!userFound.getIsActive()) {
      throw ApiException.builder()
          .message("usuario no esta activo")
          .httpStatus(HttpStatus.CONFLICT)
          .build();
    }

    boolean isValidPwd = passwordEncoder.matches(loginRequest.getPassword(), userFound.getPassword());
    if (!isValidPwd) {
      throw ApiException.builder()
          .message("password invalido")
          .httpStatus(HttpStatus.CONFLICT)
          .build();
    }

    userMapper.copyToDomainObject(loginRequest, userFound);

    return Optional.of(userFound)
        .map(userRepository::save)
        .map(userMapper::toModelObject)
        .orElseThrow();
  }

}
