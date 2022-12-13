package com.bubbles.bubbles_backend.exception;

import com.bubbles.bubbles_backend.utils.Result;

public class UserHasJoinedChatroomException extends BubblesException{
    public UserHasJoinedChatroomException(int userId, int chatroomId){
        super(String.format("User `%d` has joined chatroom `%d`!", userId, chatroomId));
    }
    @Override
    public Result result(){
        return Result.buildFailResult(ErrorCode.USER_HAS_JOINED, this.getMessage());
    }
}
