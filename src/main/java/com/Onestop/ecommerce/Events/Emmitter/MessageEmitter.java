package com.Onestop.ecommerce.Events.Emmitter;

import com.Onestop.ecommerce.Entity.UserMessages.MessageAction;
import com.Onestop.ecommerce.Entity.UserMessages.MessageStatus;
import com.Onestop.ecommerce.Entity.user.userEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class MessageEmitter extends ApplicationEvent {
    private String message;
    private MessageAction action;
    private MessageStatus status;
    private userEntity user;

    public MessageEmitter(String message, MessageAction action , MessageStatus status, userEntity user) {
        super(user);
        this.action = action;
        this.message = message;
        this.status = status;
        this.user = user;
    }
}
