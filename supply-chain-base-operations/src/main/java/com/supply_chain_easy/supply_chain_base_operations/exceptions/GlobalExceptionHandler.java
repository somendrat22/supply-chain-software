package com.supply_chain_easy.supply_chain_base_operations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ResponseEntity handleInvalidCredentialsException(InvalidCredentialsException e){
        HashMap<String, String> message = new HashMap<>();
        message.put("message", e.getMessage());
        return new ResponseEntity(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    public ResponseEntity handleUnAuthorizedException(UnAuthorizedException e){
        HashMap<String, String> message = new HashMap<>();
        message.put("message", e.getMessage());
        return new ResponseEntity(message, HttpStatus.UNAUTHORIZED);
    }

}
