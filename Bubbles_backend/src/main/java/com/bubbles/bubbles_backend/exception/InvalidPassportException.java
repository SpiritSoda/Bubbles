package com.bubbles.bubbles_backend.exception;

public class InvalidPassportException extends Exception{
    public InvalidPassportException(String token){
        super(String.format("Passport {} is not valid!", token));
    }
}
