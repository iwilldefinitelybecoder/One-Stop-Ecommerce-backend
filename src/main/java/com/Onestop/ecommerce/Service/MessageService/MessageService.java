package com.Onestop.ecommerce.Service.MessageService;

import com.Onestop.ecommerce.Dto.MessageResponse;
import com.Onestop.ecommerce.Entity.UserMessages.UserMessages;

import java.util.List;

public interface MessageService {
    List<MessageResponse> getAllMessages();
    void deleteMessage(String identifier);
    void deleteAllMessages();
    String newMessage(UserMessages userMessages);
    String updateMessage(UserMessages userMessages);
    String deleteMessage(UserMessages userMessages);
}
