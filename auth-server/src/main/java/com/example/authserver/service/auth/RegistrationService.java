package com.example.authserver.service.auth;

import com.example.authserver.domain.dto.auth.RegistrationDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RegistrationService {
    /**
     * Сохранение регистрационных данных, генерация OTP, отправка по email OTP.
     */
    void register(RegistrationDto registrationDto, HttpServletResponse response);

    /**
     * Валидация OTP и создание пользователя
     */
    void checkOtp(String otp, HttpServletRequest request);
}
