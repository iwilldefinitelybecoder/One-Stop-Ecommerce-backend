package com.Onestop.ecommerce.Events.Listner;

import com.Onestop.ecommerce.Entity.user.userEntity;
import com.Onestop.ecommerce.Events.Emmitter.ResendResetpasswordTokenEmmitter;
import com.Onestop.ecommerce.Service.MailService.MailingServices;
import com.Onestop.ecommerce.Service.UserService.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@Slf4j
@RequiredArgsConstructor
public class ResendResetPasswordListner  implements ApplicationListener<ResendResetpasswordTokenEmmitter> {
    private final TokenService service;
    private final MailingServices mailingServices;
    @Override
    public void onApplicationEvent(ResendResetpasswordTokenEmmitter event) {
        userEntity user = event.getUser();
        String token = UUID.randomUUID().toString();
        String appUrl = event.getAppUrl();

        service.saveToken(user,token);
        String body = "To verify your account, please click the link below:\n"
                + appUrl + "?token=" + token+"\n\"" +
                "If you did not request this, please ignore this email and your password will remain unchanged.";

        mailingServices.sendEmail(user.getEmail(),body,"Verification Email");
    }
}
