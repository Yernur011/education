package com.example.authserver.config.security;

import com.example.authserver.service.security.redis.RedisOAuth2AuthorizationConsentService;
import com.example.authserver.service.security.redis.RedisOAuth2AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class ConfigUtilities {

    private final AuthorizationServerProperties authorizationServerProperties;

    @Bean
    public RedisTemplate<String, OAuth2Authorization> redisTemplateOAuth2Authorization(
            RedisConnectionFactory redisConnectionFactory
    ) {
        RedisTemplate<String, OAuth2Authorization> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(
            RedisTemplate<String, OAuth2Authorization> redisTemplate
    ) {
        return new RedisOAuth2AuthorizationService(redisTemplate, authorizationServerProperties.getAuthorizationTtl());
    }
    @Bean
    public RedisTemplate<String, OAuth2AuthorizationConsent> redisTemplateOAuth2AuthorizationConsent(
            RedisConnectionFactory redisConnectionFactory
    ) {
        RedisTemplate<String, OAuth2AuthorizationConsent> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
    @Bean
    public OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService(
            RedisTemplate<String, OAuth2AuthorizationConsent> redisTemplate
    ) {
        return new RedisOAuth2AuthorizationConsentService(
                redisTemplate,
                authorizationServerProperties.getAuthorizationTtl()
        );
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public OAuth2TokenGenerator<? extends OAuth2Token> oAuth2TokenGenerator() {
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, refreshTokenGenerator);
    }
}