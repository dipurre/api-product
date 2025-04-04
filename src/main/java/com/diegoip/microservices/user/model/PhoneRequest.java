package com.diegoip.microservices.user.model;

import lombok.Data;

@Data
public class PhoneRequest {
  private String number;
  private String cityCode;
  private String countryCode;
}
