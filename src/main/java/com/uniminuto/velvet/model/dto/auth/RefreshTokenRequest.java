package com.uniminuto.velvet.model.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para renovar token")
public class RefreshTokenRequest {

    @NotBlank(message = "El refresh token es obligatorio")
    @Schema(description = "Token de actualización")
    private String refreshToken;
}