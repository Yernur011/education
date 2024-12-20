package com.example.authserver.service.security.impl;

import com.example.authserver.service.auth.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final OAuth2AuthorizationService redisOAuth2AuthorizationService;
    @Override
    public void logout(String refreshToken, String accessToken) {
        redisOAuth2AuthorizationService.remove(redisOAuth2AuthorizationService.findByToken(accessToken, OAuth2TokenType.ACCESS_TOKEN));
        redisOAuth2AuthorizationService.remove(redisOAuth2AuthorizationService.findByToken(refreshToken, OAuth2TokenType.REFRESH_TOKEN));
    }
}
