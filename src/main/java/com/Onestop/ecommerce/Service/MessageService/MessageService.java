package com.Onestop.ecommerce.Service.MessageService;

import com.Onestop.ecommerce.Dto.MessageDto.MessagesResponse;
import com.Onestop.ecommerce.Entity.UserMessages.UserMessages;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    MessagesResponse getAllMessages(String email, Pageable pageable);
    void deleteMessage(String identifier);
    void deleteAllMessages(String email);
    String newMessage(UserMessages userMessages);
    String updateMessage(List<String> messageId);

}
