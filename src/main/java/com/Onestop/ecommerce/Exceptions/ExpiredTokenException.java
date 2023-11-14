package com.Onestop.ecommerce.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpiredTokenException extends RuntimeException{
    private String message;
    public ExpiredTokenException(String message) {
        super(message);
        this.message = message;
    }
}
