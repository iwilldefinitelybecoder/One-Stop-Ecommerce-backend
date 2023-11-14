package com.Onestop.ecommerce.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidTokenException extends RuntimeException{
    private String message;
    public InvalidTokenException(String message) {
        super(message);
        this.message = message;
    }
}
