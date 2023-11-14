package com.Onestop.ecommerce.ControllerAdvice;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {

    private String message;
    private String errorCode;
    private String requestedURI;
}
