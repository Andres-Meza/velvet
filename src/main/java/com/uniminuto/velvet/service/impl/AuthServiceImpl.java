package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.config.CustomUserDetails;
import com.uniminuto.velvet.exception.BadRequestException;
import com.uniminuto.velvet.exception.UnauthorizedException;
import com.uniminuto.velvet.model.dto.auth.AuthResponse;
import com.uniminuto.velvet.model.dto.auth.LoginRequest;
import com.uniminuto.velvet.model.dto.auth.RefreshTokenRequest;
import com.uniminuto.velvet.model.entity.User;
import com.uniminuto.velvet.service.interfaces.AuthService;
import com.uniminuto.velvet.service.interfaces.UserService;
import com.uniminuto.velvet.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Value("${jwt.expiration}")
    private Long jwtExpirationMs;

    @Override
    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            log.info("Intento de login para usuario: {}", loginRequest.getUsername());

            // Autenticar usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            log.info("Login exitoso para usuario: {} - Rol: {} - Sede: {}",
                    user.getUsername(), userDetails.getRoleName(), userDetails.getLocationName());

            // Generar tokens
            String accessToken = jwtUtil.generateToken(
                    userDetails,
                    userDetails.getRoleName(),
                    userDetails.getLocationName(),
                    userDetails.getLocationId()
            );
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            // Actualizar último login usando el método específico del servicio
            userService.updateLastLogin(user.getUsername());

            // Construir response
            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .expiresIn(jwtExpirationMs)
                    .user(buildUserInfo(user))
                    .build();

        } catch (BadCredentialsException e) {
            log.error("Credenciales inválidas para usuario: {}", loginRequest.getUsername());
            throw new UnauthorizedException("Credenciales inválidas");
        } catch (DisabledException e) {
            log.error("Usuario deshabilitado: {}", loginRequest.getUsername());
            throw new UnauthorizedException("Cuenta deshabilitada");
        } catch (Exception e) {
            log.error("Error durante el login: {}", e.getMessage());
            throw new BadRequestException("Error durante la autenticación");
        }
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();

            if (!jwtUtil.validateToken(refreshToken)) {
                throw new UnauthorizedException("Refresh token inválido o expirado");
            }

            String username = jwtUtil.extractUsername(refreshToken);
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

            // Generar nuevo access token
            String newAccessToken = jwtUtil.generateToken(
                    userDetails,
                    userDetails.getRoleName(),
                    userDetails.getLocationName(),
                    userDetails.getLocationId()
            );

            return AuthResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(refreshToken)
                    .expiresIn(jwtExpirationMs)
                    .user(buildUserInfo(userDetails.getUser()))
                    .build();

        } catch (Exception e) {
            log.error("Error al renovar token: {}", e.getMessage());
            throw new UnauthorizedException("Error al renovar el token");
        }
    }

    @Override
    public void logout(String token) {
        // Implementar blacklist de tokens si es necesario
        log.info("Usuario desconectado");
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    private AuthResponse.UserInfo buildUserInfo(User user) {
        return AuthResponse.UserInfo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getName() + " " + user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .location(AuthResponse.LocationInfo.builder()
                        .id(user.getLocation().getId())
                        .name(user.getLocation().getName())
                        .address(user.getLocation().getAddress())
                        .build())
                .lastLogin(user.getLastLogin())
                .build();
    }
}