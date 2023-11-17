package com.Onestop.ecommerce.Exceptions;

import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

@Getter
@Setter
public class UserEmailNotVerifiedException extends RuntimeException{
    private String message;
    public UserEmailNotVerifiedException(String message){
        super(message);
        this.message=message;
    }
}
