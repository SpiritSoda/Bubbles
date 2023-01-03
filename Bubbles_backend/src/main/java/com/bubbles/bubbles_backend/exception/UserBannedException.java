package com.bubbles.bubbles_backend.exception;

import com.bubbles.bubbles_backend.utils.Result;

public class UserBannedException extends BubblesException{
    public UserBannedException(int userId, int chatroomId) {
        super(String.format("User %d is banned from chatroom `%d` is full!", userId, chatroomId));
    }
    @Override
    public Result result(){
        return Result.buildFailResult(ErrorCode.USER_BANNED, this.getMessage());
    }
}
