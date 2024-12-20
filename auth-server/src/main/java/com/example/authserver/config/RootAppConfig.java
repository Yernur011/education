package com.example.authserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "com.example.authserver.domain.repository")
public class RootAppConfig {
    private final AppProperties.CorsProperties corsProperties;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        corsProperties.getConfigs().forEach(configProps -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(configProps.allowCredentials());
            config.addAllowedOrigin(configProps.allowedOrigins());
            config.addAllowedOriginPattern(configProps.allowedOriginPatterns());
            config.addAllowedHeader(configProps.allowedHeaders());
            config.addExposedHeader(configProps.exposedHeaders());
            config.addAllowedMethod(configProps.allowedMethods());
            source.registerCorsConfiguration(configProps.pattern(), config);
        });
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean.getFilter();
    }

    //    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowCredentials(true);
//
//        config.addAllowedOrigin("http://127.0.0.1:8080,http://localhost:8080");
//        config.addAllowedHeader(CorsConfiguration.ALL);
//        config.addExposedHeader(CorsConfiguration.ALL);
//        config.addAllowedMethod(CorsConfiguration.ALL);
//
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return bean;
//    }
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Наш прекрасный SSO").description("Некое описание этого SSO").version("0.0.1"));
    }
}