package com.Onestop.ecommerce.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyVerifiedException extends RuntimeException{
    private String message;
    public UserAlreadyVerifiedException(String message) {
        super(message);
        this.message = message;
    }
}
