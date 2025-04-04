package com.diegoip.microservices.user.model;

import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
  private String name;
  private String email;
  private String password;
  private List<PhoneRequest> phones;
}
