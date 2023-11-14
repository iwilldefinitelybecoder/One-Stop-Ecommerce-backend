package com.Onestop.ecommerce.Events.Listner;

import com.Onestop.ecommerce.Entity.user.VerifyEmailToken;
import com.Onestop.ecommerce.Events.Emmitter.ResendVerifyToken;
import com.Onestop.ecommerce.Service.MailService.MailingServices;
import com.Onestop.ecommerce.Service.UserService.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResendVerificationTokenListner implements ApplicationListener<ResendVerifyToken>{
    private final TokenService service;
    private final MailingServices mailingServices;
    @Override
    public void onApplicationEvent(ResendVerifyToken event) {
        var token = service.generateVerificationToken(event.getToken());
        service.saveToken(token.getUser(),token.getToken());
        String body = "To verify your account, please click the link below:\n"
                + event.getAppUrl() + "?token=" + token + "\n\"" +
                "This link is valid for 10 minutes after that you have to request for New link." ;
        mailingServices.sendEmail(token.getUser().getEmail(),body,"Verification Email");

    }
}
