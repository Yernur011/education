package com.example.authserver.service.external.mail;

import com.example.authserver.domain.dto.auth.RequestToSendEmailDto;

public interface EmailSenderService {
    void sendEmail(RequestToSendEmailDto requestToSendEmailDto);

}
