package com.example.authserver.service.external.mail.impl;

import com.example.authserver.domain.dto.auth.RequestToSendEmailDto;
import com.example.authserver.service.external.mail.EmailSenderService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;
    private String sender;
    private List<String> receivers;
    private SimpleMailMessage message;


    @SneakyThrows
    public void sendEmail(RequestToSendEmailDto requestToSendEmailDto) {
        String subject = requestToSendEmailDto.getSubject();
        String text = requestToSendEmailDto.getText();
        String sender = requestToSendEmailDto.getSenderEmail();
        String receiver = requestToSendEmailDto.getReceiver();
        List<String> receivers = requestToSendEmailDto.getReceivers();

        log.info("Попытка отправки письма. Отправитель: {}, Тема: {}", sender, subject);

        if (StringUtils.isBlank(sender)) {
            log.error("Неверные значения аргументов для метода sendEmail: отправитель не указан");
            throw new BadRequestException("Invalid argument values for method sendEmail");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setSubject(subject);
        message.setText(text);
        message.setSentDate(new Date());

        if (receiver == null) {
            log.info("Получатель не указан. Получатели: {}", receivers);
            try {
                getReceiver(receivers, message);
            } catch (Exception e) {
                log.error("Ошибка при отправке письма: {}", e.getMessage(), e);
                throw new BadRequestException(e.getMessage());
            }
        } else {
            message.setTo(receiver);
            log.info("Отправка письма на получателя: {}", receiver);
            try {
                mailSender.send(message);
                log.info("Письмо успешно отправлено на получателя: {}", receiver);
            } catch (Exception e) {
                log.error("Ошибка при отправке письма на получателя: {}", receiver, e);
                throw new BadRequestException(e.getMessage());
            }
        }
    }

    @SneakyThrows
    private void getReceiver(List<String> receivers, SimpleMailMessage message) {
        if (receivers == null || receivers.isEmpty()) {
            log.warn("Список получателей пуст или не указан");
            return;
        }

        for (String receiver : receivers) {
            message.setTo(receiver);
            log.info("Отправка письма на получателя: {}", receiver);
            try {
                mailSender.send(message);
                log.info("Письмо успешно отправлено на получателя: {}", receiver);
            } catch (Exception e) {
                log.error("Ошибка при отправке письма на получателя: {}", receiver, e);
                throw new BadRequestException("Ошибка при отправке письма на адрес: " + receiver);
            }
        }
    }
}
