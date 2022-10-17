package com.bubbles.bubbles_backend.exception;

public class PasswordNotValidException extends Exception {
    public PasswordNotValidException() {
        super("Password not valid!");
    }
}
