package com.Onestop.ecommerce.Dto;

import com.Onestop.ecommerce.Entity.UserMessages.MessageAction;
import com.Onestop.ecommerce.Entity.UserMessages.MessageStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {
    private String message;
    private MessageStatus status;
    private MessageAction action;

}
