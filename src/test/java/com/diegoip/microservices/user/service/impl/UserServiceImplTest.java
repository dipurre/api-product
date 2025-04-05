package com.diegoip.microservices.user.service.impl;

import com.diegoip.microservices.user.configuration.exception.ApiException;
import com.diegoip.microservices.user.entity.UserEntity;
import com.diegoip.microservices.user.mapper.UserMapper;
import com.diegoip.microservices.user.model.LoginRequest;
import com.diegoip.microservices.user.model.UserRequest;
import com.diegoip.microservices.user.model.UserResponse;
import com.diegoip.microservices.user.repository.UserRepository;
import com.diegoip.microservices.user.util.TestUtil;
import com.diegoip.microservices.user.validator.RuleResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private UserValidator userValidator;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  void registerUserWhenIsValid() {
    UserRequest userRequest = TestUtil.parseJsonToObject("userRequestValid.json", UserRequest.class);
    UserResponse userResponseExpected = TestUtil.parseJsonToObject("userResponse.json", UserResponse.class);
    UserEntity userEntity = TestUtil.parseJsonToObject("userEntity.json", UserEntity.class);

    when(userValidator.validate(any(UserRequest.class))).thenReturn(new RuleResult());
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
    when(userMapper.toDomainObject(any(UserRequest.class))).thenReturn(userEntity);
    when(userMapper.toModelObject(any(UserEntity.class))).thenReturn(userResponseExpected);

    UserResponse userResponse = userService.register(userRequest);

    assertThat(userResponse).isEqualTo(userResponseExpected);

  }

  @Test
  void registerUserWhenAlreadyExists() {
    UserRequest userRequest = TestUtil.parseJsonToObject("userRequestValid.json", UserRequest.class);
    UserEntity userEntity = TestUtil.parseJsonToObject("userEntity.json", UserEntity.class);

    when(userValidator.validate(any(UserRequest.class))).thenReturn(new RuleResult());
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

    assertThrows(ApiException.class,
        () -> userService.register(userRequest));
  }

  @Test
  void registerUserWhenRequestIsInvalid() {
    UserRequest userRequest = TestUtil.parseJsonToObject("userRequestValid.json", UserRequest.class);
    RuleResult ruleResult = new RuleResult();
    ruleResult.addError("error");

    when(userValidator.validate(any(UserRequest.class))).thenReturn(ruleResult);

    assertThrows(ApiException.class,
        () -> userService.register(userRequest));
  }

  @Test
  void loginUserWhenIsValid() {
    LoginRequest loginRequest = TestUtil.parseJsonToObject("loginRequest.json", LoginRequest.class);
    UserResponse userResponseExpected = TestUtil.parseJsonToObject("userResponse.json", UserResponse.class);
    UserEntity userEntity = TestUtil.parseJsonToObject("userEntity.json", UserEntity.class);

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));
    when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
    when(userMapper.toModelObject(any(UserEntity.class))).thenReturn(userResponseExpected);
    doNothing().when(userMapper).copyToDomainObject(any(LoginRequest.class), any(UserEntity.class));

    UserResponse userResponse = userService.login(loginRequest);

    assertThat(userResponse).isEqualTo(userResponseExpected);
  }

  @Test
  void loginUserWhenPasswordNotMatch() {
    LoginRequest loginRequest = TestUtil.parseJsonToObject("loginRequest.json", LoginRequest.class);
    UserResponse userResponseExpected = TestUtil.parseJsonToObject("userResponse.json", UserResponse.class);
    UserEntity userEntity = TestUtil.parseJsonToObject("userEntity.json", UserEntity.class);

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));
    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

    assertThrows(ApiException.class,
        () -> userService.login(loginRequest));
  }

  @Test
  void loginUserWhenisInactive() {
    LoginRequest loginRequest = TestUtil.parseJsonToObject("loginRequest.json", LoginRequest.class);
    UserEntity userEntity = TestUtil.parseJsonToObject("userEntityInactive.json", UserEntity.class);

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

    assertThrows(ApiException.class,
        () -> userService.login(loginRequest));
  }

  @Test
  void loginUserWhenNotExists() {
    LoginRequest loginRequest = TestUtil.parseJsonToObject("loginRequest.json", LoginRequest.class);

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    assertThrows(ApiException.class,
        () -> userService.login(loginRequest));
  }

}
