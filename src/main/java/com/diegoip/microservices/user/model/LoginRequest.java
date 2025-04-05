package com.diegoip.microservices.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class LoginRequest {

  @Schema(example = "juan@rodriguez.org")
  private String email;

  @Schema(example = "Asdsdbd$5656df")
  private String password;
}