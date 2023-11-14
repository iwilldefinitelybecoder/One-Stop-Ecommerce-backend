package com.Onestop.ecommerce.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotfoundException extends RuntimeException{
    private String message;

    public  UserNotfoundException(String message) {
        super(message);
        this.message = message;
    }
}
