package com.Onestop.ecommerce.Service.MailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailingServices {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String body, String subject){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tonystarkv9@gmail.com");
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);
        javaMailSender.send(message);
        log.info("Email sent to "+to);
    }
}
