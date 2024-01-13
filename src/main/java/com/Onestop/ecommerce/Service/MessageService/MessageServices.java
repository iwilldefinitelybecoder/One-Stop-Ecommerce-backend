package com.Onestop.ecommerce.Service.MessageService;

import com.Onestop.ecommerce.Dto.MessageDto.Message;
import com.Onestop.ecommerce.Dto.MessageDto.MessagesResponse;
import com.Onestop.ecommerce.Entity.UserMessages.MessageStatus;
import com.Onestop.ecommerce.Entity.UserMessages.UserMessages;
import com.Onestop.ecommerce.Events.Emmitter.SocketMessage;
import com.Onestop.ecommerce.Repository.MessageRepo.UserMessagesRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServices  implements MessageService {

    private final UserMessagesRepo userMessagesRepo;

    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public MessagesResponse getAllMessages(String email, Pageable pageable) {
        var messages = userMessagesRepo.findAllByUserEmailOrderByCreatedAtDesc(email,pageable).orElseThrow(() -> new RuntimeException("No messages found"));
        var unseenCount = userMessagesRepo.countAllByUserEmailAndStatus(email, MessageStatus.UNSEEN).orElseThrow(() -> new RuntimeException("No messages found"));
        List<Message> messagesResponse = new ArrayList<>();

        messages.forEach(message -> {
            messagesResponse.add(Message.builder()
                    .notificationId(message.getIdentifier())
                    .message(message.getMessage())
                    .status(message.getStatus())
                    .action(message.getAction())
                    .createdAt(message.getCreatedAt())
                    .build());
        });


        return MessagesResponse.builder()
                .messages(messagesResponse)
                .unseen(unseenCount)
                .build();

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
        log.info("Message saved");
        applicationEventPublisher.publishEvent(new SocketMessage(this,"new message",userMessages.getUser().getEmail()));
        return "Message saved";
    }

    @Override
    public String updateMessage(List<String> messgeId) {
        messgeId.forEach(id -> {
            var message = userMessagesRepo.findByIdentifier(id).orElseThrow(() -> new RuntimeException("No message found"));
            message.setStatus(MessageStatus.DONE);
            userMessagesRepo.save(message);
        });
        return "Message updated";

    }

}
