package com.Onestop.ecommerce.Events.Emmitter;

import com.Onestop.ecommerce.Controller.authUser.AuthenticateRequest;
import com.Onestop.ecommerce.Controller.authUser.RegisterRequest;
import com.Onestop.ecommerce.Entity.user.userEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class VerifyTokenEmmitter extends ApplicationEvent {

    private String appUrl;
    private userEntity user;

    public VerifyTokenEmmitter(userEntity  user, String applicationUrl) {
        super(user);
        this.user = user;
        this.appUrl = applicationUrl;
    }
}
