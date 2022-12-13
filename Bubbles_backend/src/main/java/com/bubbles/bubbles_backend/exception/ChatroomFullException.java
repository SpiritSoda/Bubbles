package com.bubbles.bubbles_backend.exception;

import com.bubbles.bubbles_backend.utils.Result;

public class ChatroomFullException extends BubblesException{
    public ChatroomFullException(int chatroomId) {
        super(String.format("Chatroom ID `%d` is full!", chatroomId));
    }
    @Override
    public Result result(){
        return Result.buildFailResult(ErrorCode.CHATROOM_FULL, this.getMessage());
    }
}
