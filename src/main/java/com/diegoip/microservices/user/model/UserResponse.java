package com.diegoip.microservices.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserResponse {

  @Schema(example = "312472cc-df22-41b0-bfd3-600b628b4bc4")
  private UUID id;

  @Schema(example = "2025-04-04T18:46:05.758444")
  private LocalDateTime created;

  @Schema(example = "2025-04-04T18:46:05.758444")
  private LocalDateTime modified;

  @Schema(example = "2025-04-04T18:46:05.758444")
  private LocalDateTime lastLogin;

  @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5vcmciLCJpYXQiOjE3NDM4MTAzNjksImV4cCI6MTc0Mzg0NjM2OX0.rYfzycH15ScnR4-hi1iL1Vk9pO9Z61BiZ5fiFwylgQE")
  private String token;

  @Schema(example = "true")
  private Boolean isActive;
}
