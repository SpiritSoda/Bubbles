package com.bubbles.bubbles_backend.controller;

import com.bubbles.bubbles_backend.exception.NoTokenException;
import com.bubbles.bubbles_backend.exception.PasswordNotValidException;
import com.bubbles.bubbles_backend.exception.UserNotFoundException;
import com.bubbles.bubbles_backend.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        return new Result(-1, e.getClass().getSimpleName() + ": " + e.getMessage(), null);
    }
    @ExceptionHandler(NoTokenException.class)
    public Result handlerNoTokenException(NoTokenException e){
        return new Result(1, e.getMessage(), null);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public Result handlerUserNotFoundException(UserNotFoundException e){
        return new Result(2, e.getMessage(), null);
    }

    @ExceptionHandler(PasswordNotValidException.class)
    public Result handlePasswordNotValidException(PasswordNotValidException e){
        return new Result(3, e.getMessage(), null);
    }
}