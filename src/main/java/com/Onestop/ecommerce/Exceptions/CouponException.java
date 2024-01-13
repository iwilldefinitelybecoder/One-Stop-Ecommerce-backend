package com.Onestop.ecommerce.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponException extends RuntimeException{
    private String message;
    public CouponException(String message) {
        super(message);
        this.message = message;
    }
}
