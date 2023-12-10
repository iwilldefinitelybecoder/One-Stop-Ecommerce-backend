package com.Onestop.ecommerce.Events.Emmitter;

import com.Onestop.ecommerce.Entity.UserMessages.MessageAction;
import com.Onestop.ecommerce.Entity.UserMessages.MessageStatus;
import com.Onestop.ecommerce.Entity.user.userEntity;
import org.springframework.context.ApplicationEvent;

public class MessageEmmiter extends ApplicationEvent {
    private String message;
    private MessageAction action;
    private MessageStatus status;
    private userEntity user;

    public MessageEmmiter(String message,MessageAction action ,MessageStatus status,userEntity user) {
        super(user);
        this.action =
    }
}
