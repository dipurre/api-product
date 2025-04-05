package com.diegoip.microservices.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PhoneRequest {

  @Schema(example = "1234567")
  private String number;

  @Schema(example = "1")
  private String cityCode;

  @Schema(example = "57")
  private String countryCode;
}
