package com.example.authserver.config.security;

import com.example.authserver.domain.dto.auth.AuthorizedUser;
import com.example.authserver.domain.dto.auth.IntrospectionPrincipal;
import com.example.authserver.domain.dto.auth.TokenInfoDto;
import com.example.authserver.config.grant_type.OAuth2PasswordAuthenticationConverter;
import com.example.authserver.config.grant_type.OAuth2PasswordTokenAuthenticationProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenIntrospection;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenIntrospectionAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    private final static String principalAttributeKey = "java.security.Principal";

    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;
    private final AuthorizationServerProperties authorizationServerProperties;
    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private final AuthenticationManager authenticationManager;


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        var authorizationServerConfigurer = getoAuth2AuthorizationServerConfigurer();

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        http.securityMatcher(endpointsMatcher)
                .authorizeHttpRequests(authorize ->
                        authorize
                                // ендпоинты swagger вынесем из под security
                                .requestMatchers(
                                        "/v3/api-docs"
                                        )
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .apply(authorizationServerConfigurer);
         return http.build();
    }

    private OAuth2AuthorizationServerConfigurer getoAuth2AuthorizationServerConfigurer() {
        var authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        authorizationServerConfigurer.tokenIntrospectionEndpoint((config) -> {
            config.introspectionResponseHandler(this::introspectionResponse);
        });
        authorizationServerConfigurer.tokenEndpoint(customizer -> {
            customizer.accessTokenRequestConverter(new OAuth2PasswordAuthenticationConverter());
            customizer.authenticationProvider(new OAuth2PasswordTokenAuthenticationProvider(
                    authorizationService,
                    tokenGenerator,
                    authenticationManager
            ));
        });
        return authorizationServerConfigurer;
    }

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer(authorizationServerProperties.getIssuerUrl())
                .tokenIntrospectionEndpoint(authorizationServerProperties.getIntrospectionEndpoint())
                .build();
    }

    private void introspectionResponse(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2TokenIntrospectionAuthenticationToken introspectionAuthenticationToken = (OAuth2TokenIntrospectionAuthenticationToken) authentication;
        TokenInfoDto.TokenInfoDtoBuilder tokenInfoDtoBuilder = TokenInfoDto.builder().active(false);                        // создаём билдер объекта ответа
        if (introspectionAuthenticationToken.getTokenClaims().isActive()) {                                                 // если токен активен, то заполняем все параметры информации о токене и далее пытаемся получить информацию о пользователе
            OAuth2TokenIntrospection claims = introspectionAuthenticationToken.getTokenClaims();
            tokenInfoDtoBuilder.active(true)
                    .sub(claims.getSubject())
                    .aud(claims.getAudience())
                    .nbf(claims.getNotBefore())
                    .scopes(claims.getScopes())
                    .iss(claims.getIssuer())
                    .exp(claims.getExpiresAt())
                    .iat(claims.getIssuedAt())
                    .jti(claims.getId())
                    .clientId(claims.getClientId())
                    .tokenType(claims.getTokenType());


            String token = introspectionAuthenticationToken.getToken();                                                      // получаем значение токена, который проверяется
            OAuth2Authorization tokenAuth = oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);     // предполагая что это ACCESS TOKEN, пытаемся получить объект OAuth2Authorization из OAuth2AuthorizationService
            if (tokenAuth != null) {
                Authentication attributeAuth = tokenAuth.getAttribute(principalAttributeKey);                                // Если найден этот объект OAuth2Authorization, то получаем из него объект Authentication следующим образом
                if (attributeAuth != null) {
                    if (attributeAuth.getPrincipal() instanceof AuthorizedUser authorizedUser) {                             // Если полученный объект Authentication не пуст, то проверяем является ли его principal экземпляром класса AuthorizedUser
                        tokenInfoDtoBuilder.principal(IntrospectionPrincipal.build(authorizedUser));                         // Создаём IntrospectionPrincipal на его основе
                    } else {                                                                                                 // Иначе выбрасываем исключение, что другие типы principal мы не поддерживаем
                        throw new RuntimeException("Principal class = " + attributeAuth.getPrincipal().getClass().getSimpleName() + " is not supported");
                    }
                }
            }
        }

        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        mappingJackson2HttpMessageConverter.write(tokenInfoDtoBuilder.build(), null, httpResponse);              // Превращаем наш TokenInfoDto в json строку и отправляем её через ServletServerHttpResponse
    }
}