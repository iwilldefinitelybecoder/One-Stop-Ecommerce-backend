package com.Onestop.ecommerce.Events.Listner;

import com.Onestop.ecommerce.Events.Emmitter.ResetPasswordEmmitter;
import com.Onestop.ecommerce.Service.MailService.MailingServices;
import com.Onestop.ecommerce.Service.UserService.TokenService;
import com.Onestop.ecommerce.Service.UserService.UserServices;
import com.Onestop.ecommerce.Service.authService.AuthenticateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ResetPasswordListner implements ApplicationListener<ResetPasswordEmmitter> {

    private MailingServices mailingServices;
    private TokenService tokenService;
    private AuthenticateService authenticateService;

    @Override
    public void onApplicationEvent(ResetPasswordEmmitter event) {
        var user = authenticateService.getUser(event.getUser());
        String token = UUID.randomUUID().toString();
        String appUrl = event.getAppUrl();
        String res = tokenService.saveResetPasswordToken(user,token);
        if(res.equals("SUCCESS")){
            String body = "To reset your password, please click the link below:\n"
                    + appUrl + "?token=" + token+"\n\"" +
                    "If you did not request this, please ignore this email and your password will remain unchanged.";
            mailingServices.sendEmail(user.getEmail(),body,"Reset Password");
        }
        else{
            log.info("Error in saving token");
        }

    }
}
