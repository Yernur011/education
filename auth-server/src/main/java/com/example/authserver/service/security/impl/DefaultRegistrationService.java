package com.example.authserver.service.security.impl;

import com.example.authserver.domain.dto.auth.RegistrationDto;
import com.example.authserver.domain.dto.auth.RequestToSendEmailDto;
import com.example.authserver.domain.exceptions.InformationException;
import com.example.authserver.domain.exceptions.RegistrationException;
import com.example.authserver.service.external.mail.EmailSenderService;
import com.example.authserver.service.external.otp.OTPStore;
import com.example.authserver.service.security.RegistrationService;
import com.example.authserver.service.security.redis.RegistrationStore;
import com.example.authserver.service.security.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultRegistrationService implements RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(DefaultRegistrationService.class);
    private final UserService userService;
    private final EmailSenderService emailSender;
    private final OTPStore otpStore;
    private final RegistrationStore registrationStore;


    @Override
    public void register(RegistrationDto registrationDto, HttpServletResponse response) {
        log.info("Начало регистрации для пользователя с email: {}", registrationDto.getEmail());

        if (userService.existByEmail(registrationDto.getEmail())) {
            log.warn("Учетная запись с email {} уже существует", registrationDto.getEmail());
            throw new InformationException("Учетная запись с таким e-mail уже существует");
        }

        log.info("Генерация OTP для регистрации пользователя");
        OTPStore.GenerationResult generationResult = otpStore.generate(response);

        try {
            log.info("Сохранение данных пользователя в registrationStore с sessionId: {}", generationResult.sessionId());
            registrationStore.save(registrationDto, generationResult.sessionId());
        } catch (Exception e) {
            log.error("Ошибка при сохранении данных пользователя в registrationStore", e);
            throw new InformationException("Произошла непредвиденная ошибка");
        }
        log.info("Подготовка письма для отправки на email: {}", registrationDto.getEmail());
        var request = new RequestToSendEmailDto();
        request.setSenderEmail("ToyToyla@gmail.com");
        request.setReceiver(registrationDto.getEmail());
        request.setSubject("Подтверждение регистрации");
        String text = registrationDto.getFirstName() + " Ваш код подтверждения: " + generationResult.otp() +
                ". Пожалуйста, введите этот код для завершения вашей аутентификации. Никому не сообщайте этот код.";
        request.setText(text);
        log.info("Отправка письма для пользователя: {}", registrationDto.getEmail());
        emailSender.sendEmail(request);
        log.info("Письмо успешно отправлено на email: {}", registrationDto.getEmail());
    }

    @Override
    @Transactional
    public void checkOtp(String otp, HttpServletRequest request) {
        log.info("Начало проверки OTP для сессии с request: {}", request.getSession().getId());
        if (!otpStore.validate(otp, request)) {
            log.warn("Неверный OTP код: {}", otp);
            throw new RegistrationException("Код подтверждения не верный");
        }
        String sessionId = otpStore.getSessionId(request);
        log.info("OTP проверен успешно. Получен sessionId: {}", sessionId);
        RegistrationDto registrationDto;
        try {
            log.info("Попытка получения данных регистрации для sessionId: {}", sessionId);
            registrationDto = registrationStore.take(sessionId);
        } catch (Exception e) {
            log.error("Ошибка при получении данных регистрации для sessionId: {}", sessionId, e);
            throw new InformationException("Произошла непредвиденная ошибка");
        }
        log.info("Данные регистрации получены успешно для пользователя с email: {}", registrationDto.getEmail());
        log.info("Попытка сохранения и активации пользователя с email: {}", registrationDto.getEmail());
        userService.saveAndActivateUser(registrationDto);
        log.info("Пользователь с email: {} успешно активирован", registrationDto.getEmail());
    }
}