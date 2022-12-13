package com.bubbles.bubbles_backend.exception;

import com.bubbles.bubbles_backend.utils.Result;

public class PasswordNotValidException extends BubblesException {
    public PasswordNotValidException() {
        super("Password not valid!");
    }

    @Override
    public Result result(){
        return Result.buildFailResult(ErrorCode.PASSWORD_NOT_VALID, this.getMessage());
    }
}
