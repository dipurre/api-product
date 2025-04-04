package com.diegoip.microservices.user.service.impl;

import com.diegoip.microservices.user.entity.UserEntity;
import com.diegoip.microservices.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userFound = userRepository
        .findByEmail(username)
        .orElseThrow(() ->  new UsernameNotFoundException(format("el usuario %s no existe", username)));

    if (!userFound.getIsActive()) {
      throw new UsernameNotFoundException(format("el usuario %s no esta activo", username));
    }

    return User.withUsername(userFound.getEmail())
        .password(userFound.getPassword())
        .build();
  }
}
