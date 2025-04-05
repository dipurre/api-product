package com.diegoip.microservices.user.controller.openapi;

import com.diegoip.microservices.user.model.LoginRequest;
import com.diegoip.microservices.user.model.UserRequest;
import com.diegoip.microservices.user.model.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "usuarios")
public interface UserControllerOpenApi {

  @Operation(summary = "registrar un usuario", responses = {
      @ApiResponse(responseCode = "201", description = "usuario registrado"),
  })
  UserResponse register(UserRequest userRequest);

  @Operation(summary = "logear a un usuario", responses = {
      @ApiResponse(responseCode = "200", description = "usuario se logea"),
  })
  UserResponse login(@RequestBody LoginRequest loginRequest);
}
