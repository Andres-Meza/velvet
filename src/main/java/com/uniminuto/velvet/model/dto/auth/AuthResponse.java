package com.uniminuto.velvet.model.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response de autenticación exitosa")
public class AuthResponse {

    @Schema(description = "Token de acceso JWT")
    private String accessToken;

    @Schema(description = "Token de actualización")
    private String refreshToken;

    @Schema(description = "Tipo de token", example = "Bearer")
    @Builder.Default
    private String tokenType = "Bearer";

    @Schema(description = "Tiempo de expiración en milisegundos")
    private Long expiresIn;

    @Schema(description = "Información del usuario autenticado")
    private UserInfo user;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Información del usuario")
    public static class UserInfo {

        @Schema(description = "ID del usuario")
        private Long id;

        @Schema(description = "Nombre de usuario")
        private String username;

        @Schema(description = "Nombre completo")
        private String fullName;

        @Schema(description = "Email del usuario")
        private String email;

        @Schema(description = "Rol del usuario")
        private String role;

        @Schema(description = "Información de la sede")
        private LocationInfo location;

        @Schema(description = "Fecha del último login")
        private LocalDateTime lastLogin;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Información de la sede")
    public static class LocationInfo {

        @Schema(description = "ID de la sede")
        private Long id;

        @Schema(description = "Nombre de la sede")
        private String name;

        @Schema(description = "Dirección de la sede")
        private String address;
    }
}