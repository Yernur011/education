package com.example.authserver.service.security.auth;

public interface LogoutService {
    void logout(String refreshToken, String accessToken);
}
