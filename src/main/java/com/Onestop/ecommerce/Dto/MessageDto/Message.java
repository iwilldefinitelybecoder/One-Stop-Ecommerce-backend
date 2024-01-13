package com.Onestop.ecommerce.Dto.MessageDto;

import com.Onestop.ecommerce.Entity.UserMessages.MessageAction;
import com.Onestop.ecommerce.Entity.UserMessages.MessageStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Message {

    private String message;
    private MessageStatus status;
    private MessageAction action;
    private String notificationId;
    private Date createdAt;
}
