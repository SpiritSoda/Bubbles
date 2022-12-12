package com.bubbles.bubbles_backend.controller;

import com.bubbles.bubbles_backend.exception.*;
import com.bubbles.bubbles_backend.utils.Result;
import io.jsonwebtoken.ExpiredJwtException;
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

    @ExceptionHandler(UserHasJoinedChatroomException.class)
    public Result handleUserHasJoinedChatroomException(UserHasJoinedChatroomException e){
        return new Result(4, e.getMessage(), null);
    }

    @ExceptionHandler(ChatroomNotFoundException.class)
    public Result handlerChatroomNotFoundException(ChatroomNotFoundException e){
        return new Result(5, e.getMessage(), null);
    }

    @ExceptionHandler(InvalidPassportException.class)
    public Result handlerInvalidPassportException(InvalidPassportException e){
        return new Result(6, e.getMessage(), null);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public Result handlerExpiredJwtException(ExpiredJwtException e){
        return new Result(7, e.getMessage(), null);
    }
}