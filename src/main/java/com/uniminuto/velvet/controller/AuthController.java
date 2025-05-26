package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.config.CustomUserDetails;
import com.uniminuto.velvet.model.dto.auth.AuthResponse;
import com.uniminuto.velvet.model.dto.auth.LoginRequest;
import com.uniminuto.velvet.model.dto.auth.RefreshTokenRequest;
import com.uniminuto.velvet.service.interfaces.AuthService;
import com.uniminuto.velvet.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v4/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para autenticación y manejo de tokens")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve tokens JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar token", description = "Genera un nuevo access token usando el refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token renovado exitosamente"),
            @ApiResponse(responseCode = "401", description = "Refresh token inválido"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthResponse response = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Invalida el token actual del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout exitoso"),
            @ApiResponse(responseCode = "401", description = "Token inválido")
    })
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        String token = getJwtFromRequest(request);
        if (token != null) {
            authService.logout(token);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Sesión cerrada exitosamente");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    @Operation(summary = "Validar token", description = "Valida si el token actual es válido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token válido"),
            @ApiResponse(responseCode = "401", description = "Token inválido")
    })
    public ResponseEntity<Map<String, Object>> validateToken(HttpServletRequest request) {
        String token = getJwtFromRequest(request);
        Map<String, Object> response = new HashMap<>();

        if (token != null && authService.validateToken(token)) {
            // Extraer información del token
            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);
            String locationName = jwtUtil.extractLocationName(token);
            Long locationId = jwtUtil.extractLocationId(token);

            response.put("valid", true);
            response.put("username", username);
            response.put("role", role);
            response.put("locationName", locationName);
            response.put("locationId", locationId);
            response.put("expiresAt", jwtUtil.extractExpiration(token));
        } else {
            response.put("valid", false);
            response.put("message", "Token inválido o expirado");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "Información del usuario actual", description = "Obtiene la información del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado")
    })
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", userDetails.getUserId());
            userInfo.put("username", userDetails.getUsername());
            userInfo.put("fullName", userDetails.getFullName());
            userInfo.put("role", userDetails.getRoleName());
            userInfo.put("locationId", userDetails.getLocationId());
            userInfo.put("locationName", userDetails.getLocationName());
            userInfo.put("authorities", userDetails.getAuthorities());

            return ResponseEntity.ok(userInfo);
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Usuario no autenticado");
        return ResponseEntity.status(401).body(errorResponse);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}