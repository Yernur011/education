package com.example.authserver.service.auth;

public interface LogoutService {
    void logout(String refreshToken, String accessToken);
}
