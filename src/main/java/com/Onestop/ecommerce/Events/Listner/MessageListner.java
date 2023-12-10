package com.Onestop.ecommerce.Events.Listner;

import com.Onestop.ecommerce.Entity.UserMessages.UserMessages;
import com.Onestop.ecommerce.Events.Emmitter.MessageEmitter;
import com.Onestop.ecommerce.Service.MessageService.MessageService;
import com.Onestop.ecommerce.Service.MessageService.MessageServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageListner implements ApplicationListener<MessageEmitter> {

    private final MessageServices messageServices;

    @Override
    public void onApplicationEvent(MessageEmitter event) {
        var message  = UserMessages.builder()
                .message(event.getMessage())
                .action(event.getAction())
                .status(event.getStatus())
                .user(event.getUser())
                .build();
        messageServices.newMessage(message);

    }
}
