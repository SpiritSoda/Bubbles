package com.bubbles.bubbles_backend.exception;

import com.bubbles.bubbles_backend.utils.Result;

public class UserExistsException extends BubblesException{

    public UserExistsException(String username) {
        super(String.format("Username `%s` exists!", username));
    }

    @Override
    public Result result(){
        return Result.buildFailResult(ErrorCode.USER_EXIST, this.getMessage());
    }
}
