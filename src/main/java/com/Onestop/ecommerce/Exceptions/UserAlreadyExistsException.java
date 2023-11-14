package com.Onestop.ecommerce.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyExistsException extends RuntimeException{
    private String message;
    public UserAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }




}
