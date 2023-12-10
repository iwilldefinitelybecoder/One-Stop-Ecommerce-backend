package com.Onestop.ecommerce.Service.MessageService;

import com.Onestop.ecommerce.Entity.UserMessages.UserMessages;
import com.Onestop.ecommerce.Repository.MessageRepo.UserMessagesRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServices  implements MessageService {

    private final UserMessagesRepo userMessagesRepo;


    @Override
    public List<UserMessages> getAllMessages() {
        return null;
    }

    @Override
    public void deleteMessage(String identifier) {

    }

    @Override
    public void deleteAllMessages() {

    }

    @Override
    public String newMessage(UserMessages userMessages) {
        return null;
    }

    @Override
    public String updateMessage(UserMessages userMessages) {
        return null;
    }

    @Override
    public String deleteMessage(UserMessages userMessages) {
        return null;
    }
}
