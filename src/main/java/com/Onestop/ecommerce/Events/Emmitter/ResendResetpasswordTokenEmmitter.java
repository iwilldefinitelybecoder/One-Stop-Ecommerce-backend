package com.Onestop.ecommerce.Events.Emmitter;

import com.Onestop.ecommerce.Entity.user.userEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class ResendResetpasswordTokenEmmitter extends ApplicationEvent {
    private String appUrl;
    private userEntity user;

    public ResendResetpasswordTokenEmmitter(userEntity user, String applicationUrl) {
        super(user);
        this.user = user;
        this.appUrl = applicationUrl;
    }
}
