package com.example.authserver.config;

import com.example.authserver.service.external.otp.OTPStore;
import com.example.authserver.service.external.otp.impl.RedisOTPStore;
import com.example.authserver.service.security.redis.RedisRegistrationStore;
import com.example.authserver.service.security.redis.RegistrationStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class BeanConfig {
    @Bean
    public OTPStore.Config otpStoreConfig(
            @Value("${otp-store.cookie-name:default-name}") String cookieName,
            @Value("${otp-store.cookie-domain:localhost}") String cookieDomain,
            @Value("${otp-store.cookie-max-age:180}") int cookieMaxAge
    ) {
        return new OTPStore.Config(cookieName, cookieDomain, cookieMaxAge);
    }

    @Bean
    public OTPStore otpStore(OTPStore.Config otpStoreConfig, StringRedisTemplate redisTemplate) {
        return new RedisOTPStore(otpStoreConfig, redisTemplate);
    }

    @Bean
    public RegistrationStore registrationStore(
            OTPStore.Config otpStoreConfig,
            StringRedisTemplate redisTemplate,
            ObjectMapper objectMapper
    ) {
        return new RedisRegistrationStore(otpStoreConfig.cookieMaxAge(), redisTemplate, objectMapper);
    }
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("tka99.ru@gmail.com");
        mailSender.setPassword("xpuw zxdb hqhu ntis");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
        return mailSender;
    }


//    @Bean
//    public ResetPasswordStore resetPasswordStore(
//            OTPStore.Config otpStoreConfig,
//            StringRedisTemplate redisTemplate,
//            ObjectMapper objectMapper
//    ) {
//        return new RedisResetPasswordStore(otpStoreConfig.cookieMaxAge(), redisTemplate, objectMapper);
//    }
}
