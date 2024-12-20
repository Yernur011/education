package com.example.authserver.controller;

import com.example.authserver.service.auth.LogoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-token")
public class LogoutController {

    private final LogoutService logoutServiceImpl;

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam("refresh") String refreshToken, @RequestParam("access") String accessToken) {
        logoutServiceImpl.logout(refreshToken, accessToken);
        return ResponseEntity.ok("Logout successful");
    }
}
