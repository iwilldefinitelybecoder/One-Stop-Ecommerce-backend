package com.Onestop.ecommerce.Events.Emmitter;

import com.Onestop.ecommerce.Entity.user.userEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ResendVerifyToken extends ApplicationEvent {
    private String appUrl;
    private String token;

    public ResendVerifyToken(String token, String applicationUrl) {
        super(token);
        this.token = token;
        this.appUrl = applicationUrl;
    }
}
