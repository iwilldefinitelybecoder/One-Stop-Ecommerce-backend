package com.Onestop.ecommerce.Service.MessageService;

import com.Onestop.ecommerce.Entity.UserMessages.UserMessages;

import java.util.List;

public interface MessageServices {
    List<UserMessages> getAllMessages();
    void deleteMessage(Long messageId);
}
