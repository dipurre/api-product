package com.diegoip.microservices.user.controller.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "test")
public interface TestControllerOpenApi {

  @Operation(summary = "imprime una cadenade prueba",
      responses = {
          @ApiResponse(responseCode = "200", description = "imprime cadena"),
      })
  String test();
}
