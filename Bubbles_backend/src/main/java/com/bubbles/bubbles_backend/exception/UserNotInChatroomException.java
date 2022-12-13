package com.bubbles.bubbles_backend.exception;

import com.bubbles.bubbles_backend.utils.Result;

public class UserNotInChatroomException extends BubblesException {
    public UserNotInChatroomException(int userId, int chatroomId) {
        super(String.format("User `%d` is not in chatroom `%d`!", userId, chatroomId));
    }
    @Override
    public Result result(){
        return Result.buildFailResult(ErrorCode.NOT_IN_CHATROOM, this.getMessage());
    }
}
