package com.bubbles.bubbles_backend.exception;

import com.bubbles.bubbles_backend.utils.Result;

public class MessageNotFoundException extends BubblesException{
    public MessageNotFoundException(int messageId) {
        super(String.format("Message ID `%d` not found!", messageId));
    }
    @Override
    public Result result(){
        return Result.buildFailResult(ErrorCode.MESSAGE_NOT_FOUND, this.getMessage());
    }
}
