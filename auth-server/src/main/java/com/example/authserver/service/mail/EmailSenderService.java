package com.example.authserver.service.mail;

import com.example.authserver.domain.dto.auth.RequestToSendEmailDto;

public interface EmailSenderService {
    void sendEmail(RequestToSendEmailDto requestToSendEmailDto);

}
