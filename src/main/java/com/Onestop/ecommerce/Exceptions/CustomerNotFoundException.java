package com.Onestop.ecommerce.Exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Getter
@Setter
public class CustomerNotFoundException extends UsernameNotFoundException {
    private String msg;

    public CustomerNotFoundException(String msg) {
        super(msg);
    }
}
