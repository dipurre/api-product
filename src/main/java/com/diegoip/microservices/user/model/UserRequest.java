package com.diegoip.microservices.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UserRequest {

  @Schema(example = "Juan Rodriguez")
  private String name;

  @Schema(example = "juan@rodriguez.org")
  private String email;

  @Schema(example = "Asdsdbd$5656df")
  private String password;

  private List<PhoneRequest> phones;
}
