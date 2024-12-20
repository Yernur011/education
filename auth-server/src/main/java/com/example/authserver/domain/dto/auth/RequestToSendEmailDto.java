package com.example.authserver.domain.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestToSendEmailDto {
    private String receiver;
    private List<String> receivers;
    private String senderEmail;
    private String subject;
    private String text;
}
