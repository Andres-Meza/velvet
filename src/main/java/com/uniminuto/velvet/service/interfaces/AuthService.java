package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.auth.AuthResponse;
import com.uniminuto.velvet.model.dto.auth.LoginRequest;
import com.uniminuto.velvet.model.dto.auth.RefreshTokenRequest;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    void logout(String token);

    boolean validateToken(String token);
}