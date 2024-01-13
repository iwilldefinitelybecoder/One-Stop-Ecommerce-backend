package com.Onestop.ecommerce.Dto.MessageDto;

import com.Onestop.ecommerce.Entity.UserMessages.MessageAction;
import com.Onestop.ecommerce.Entity.UserMessages.MessageStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MessagesResponse {
    List<Message> messages;
    private int unseen;

}
