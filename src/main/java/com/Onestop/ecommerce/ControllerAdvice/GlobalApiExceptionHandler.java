package com.Onestop.ecommerce.ControllerAdvice;

import com.Onestop.ecommerce.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalApiExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse handleUserNotFoundException(UserAlreadyExistsException ex) {
       ExceptionResponse response = new ExceptionResponse();
       response.setMessage(ex.getMessage());
         response.setErrorCode("409");
            response.setRequestedURI("http://localhost:8080/api/v1/user");
            return response;
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse handleInvalidTokenException(InvalidTokenException ex) {
       ExceptionResponse response = new ExceptionResponse();
       response.setMessage(ex.getMessage());
         response.setErrorCode("409");
            response.setRequestedURI("http://localhost:8080/api/v1/Auth/verifyToken");
            return response;
    }

    @ExceptionHandler(ExpiredTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse handleExpiredTokenException(ExpiredTokenException ex) {
       ExceptionResponse response = new ExceptionResponse();
       response.setMessage(ex.getMessage());
         response.setErrorCode("409");
            response.setRequestedURI("http://localhost:8080/api/v1/Auth/verifyToken");
            return response;
    }

    @ExceptionHandler(UserAlreadyVerifiedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ExceptionResponse handleUserAlreadyExistsException(UserAlreadyVerifiedException ex) {
       ExceptionResponse response = new ExceptionResponse();
       response.setMessage(ex.getMessage());
         response.setErrorCode("409");
            response.setRequestedURI("http://localhost:8080/api/v1/Auth");
            return response;
    }

    @ExceptionHandler(UserNotfoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionResponse handleUserNotFoundException(UserNotfoundException ex) {
       ExceptionResponse response = new ExceptionResponse();
       response.setMessage(ex.getMessage());
         response.setErrorCode("404");
            response.setRequestedURI("http://localhost:8080/api/v1/user");
            return response;
    }
}
