package com.bubbles.bubbles_backend.exception;

import com.bubbles.bubbles_backend.utils.Result;

public class TokenNotFoundException extends BubblesException {
    public TokenNotFoundException(String msg) {
        super(msg);
    }
    @Override
    public Result result(){
        return Result.buildFailResult(ErrorCode.TOKEN_NOT_FOUND, this.getMessage());
    }
}
