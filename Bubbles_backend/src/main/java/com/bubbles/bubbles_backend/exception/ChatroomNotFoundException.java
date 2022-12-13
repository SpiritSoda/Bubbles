package com.bubbles.bubbles_backend.exception;

import com.bubbles.bubbles_backend.utils.Result;

public class ChatroomNotFoundException extends BubblesException {
    public ChatroomNotFoundException(int chatroomId) {
        super(String.format("Chatroom ID `%d` not found!", chatroomId));
    }
    @Override
    public Result result(){
        return Result.buildFailResult(ErrorCode.CHATROOM_NOT_FOUND, this.getMessage());
    }
}
