package com.Onestop.ecommerce.Service.MessageService;

import com.Onestop.ecommerce.Dto.MessageResponse;
import com.Onestop.ecommerce.Entity.UserMessages.MessageStatus;
import com.Onestop.ecommerce.Entity.UserMessages.UserMessages;
import com.Onestop.ecommerce.Repository.MessageRepo.UserMessagesRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServices  implements MessageService {

    private final UserMessagesRepo userMessagesRepo;


    @Override
    public List<MessageResponse> getAllMessages(String email) {
        var messages = userMessagesRepo.findAllByUserEmail(email).orElseThrow(() -> new RuntimeException("No messages found"));
        List<MessageResponse> messageResponses = new ArrayList<>();
        messages.forEach(message -> {
            var messageResponse = MessageResponse.builder()
                    .message(message.getMessage())
                    .action(message.getAction())
                    .status(message.getStatus())
                    .build();
            messageResponses.add(messageResponse);

        });
        return messageResponses;

    }

    @Override
    public void deleteMessage(String identifier) {

        userMessagesRepo.deleteByIdentifier(identifier);

    }

    @Override
    public void deleteAllMessages(String email) {
        userMessagesRepo.deleteAllByUserEmail(email);

    }

    @Override
    public String newMessage(UserMessages userMessages) {
        userMessagesRepo.save(userMessages);
        return "Message saved";
    }

    @Override
    public String updateMessage(String messgeId) {
        var message = userMessagesRepo.findByIdentifier(messgeId).orElseThrow(() -> new RuntimeException("No message found"));
        message.setStatus(MessageStatus.DONE);
        userMessagesRepo.save(message);
        return "Message updated";

    }

}
