package com.Onestop.ecommerce.Service.MessageService;

import com.Onestop.ecommerce.Dto.MessageResponse;
import com.Onestop.ecommerce.Entity.UserMessages.UserMessages;

import java.util.List;

public interface MessageService {
    List<MessageResponse> getAllMessages(String email);
    void deleteMessage(String identifier);
    void deleteAllMessages(String email);
    String newMessage(UserMessages userMessages);
    String updateMessage(String messageId);

}
