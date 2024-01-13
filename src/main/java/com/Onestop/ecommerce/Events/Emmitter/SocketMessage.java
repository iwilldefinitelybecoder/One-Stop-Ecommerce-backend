package com.Onestop.ecommerce.Events.Emmitter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SocketMessage extends ApplicationEvent {

    private String message;
    private String userId;

    public SocketMessage(Object source, String message,String userId) {
        super(source);
        this.message = message;
        this.userId = userId;
    }
}
