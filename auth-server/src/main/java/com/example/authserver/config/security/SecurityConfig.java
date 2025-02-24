package com.example.authserver.config.security;

import com.example.authserver.service.security.impl.CustomOAuth2UserService;
import com.example.authserver.service.security.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailsService userDetailService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        SocialConfigurer socialConfigurer = new SocialConfigurer()
                .oAuth2UserService(customOAuth2UserService);
        http.apply(socialConfigurer);
        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(customOAuth2UserService)
                )
        );
        http.csrf(AbstractHttpConfigurer::disable);

        http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
        http.exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.authorizeHttpRequests(authorize ->
                authorize
                        // endpoint-ы swagger вынесем из под security
                        .requestMatchers(
                                "/v3/api-docs",
                                "/swagger-ui/**",
                                "/v3/api-docs/swagger-config",
                                "registration/**",
                                "/user-token/**",
                                "/actuator/**"
//                                , "/api/v1/**"
                        ).permitAll()
                        .anyRequest().authenticated()
        );
        return http.formLogin(withDefaults()).build();

    }
}