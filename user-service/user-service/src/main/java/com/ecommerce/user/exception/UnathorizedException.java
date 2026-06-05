package com.ecommerce.user.exception;

public class UnathorizedException extends RuntimeException{

    public UnathorizedException(String message){
        super(message);
    }

    public UnathorizedException(String message, Throwable cause){
        super(message, cause);
    }
}
