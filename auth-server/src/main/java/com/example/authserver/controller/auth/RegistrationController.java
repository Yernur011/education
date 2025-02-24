package com.example.authserver.controller.auth;

import com.example.authserver.domain.dto.auth.RegistrationDto;
import com.example.authserver.service.security.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/init")
    public ResponseEntity<String> registerNewUser(@RequestBody @Valid RegistrationDto dto,HttpServletResponse response) {
            registrationService.register(dto,response);
            return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно зарегистрирован.");
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> checkOtp(@RequestParam("otp") String otp, HttpServletRequest request) {
            registrationService.checkOtp(otp, request);
            return ResponseEntity.ok("OTP подтвержден успешно.");
    }
}