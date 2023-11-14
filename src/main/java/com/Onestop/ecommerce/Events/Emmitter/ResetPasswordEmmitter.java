package com.Onestop.ecommerce.Events.Emmitter;

import com.Onestop.ecommerce.Entity.user.userEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ResetPasswordEmmitter extends ApplicationEvent {
    private String appUrl;
    private String user;

    public ResetPasswordEmmitter(String user, String applicationUrl) {
        super(user);
        this.user = user;
        this.appUrl = applicationUrl;
    }
}
