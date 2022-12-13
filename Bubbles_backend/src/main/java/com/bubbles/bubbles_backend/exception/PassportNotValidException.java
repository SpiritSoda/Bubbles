package com.bubbles.bubbles_backend.exception;

import com.bubbles.bubbles_backend.utils.Result;

public class PassportNotValidException extends BubblesException{
    public PassportNotValidException(String token){
        super(String.format("Passport `%s` is not valid!", token));
    }

    @Override
    public Result result(){
        return Result.buildFailResult(ErrorCode.PASSPORT_NOT_VALID, this.getMessage());
    }
}
