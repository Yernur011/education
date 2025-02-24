package com.example.authserver.service.security.impl;

import com.example.authserver.domain.enums.auth.AuthProvider;
import com.example.authserver.service.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Попытка загрузки OAuth2 пользователя. ClientRegistrationId: {}", userRequest.getClientRegistration().getRegistrationId());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2 пользователь загружен успешно: {}", oAuth2User.getName());

        String clientRegId = userRequest.getClientRegistration().getRegistrationId();
        AuthProvider provider = AuthProvider.findByName(clientRegId);
        log.info("Определен провайдер аутентификации: {}", provider);

        log.info("Сохранение пользователя и преобразование данных.");
        return userService.saveAndMap(oAuth2User, provider);
    }
}

