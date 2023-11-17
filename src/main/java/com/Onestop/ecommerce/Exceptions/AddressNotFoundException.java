package com.Onestop.ecommerce.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressNotFoundException extends RuntimeException{
    private String message;

    public AddressNotFoundException(String message){
        super(message);
        this.message = message;
    }
}
