package com.bubbles.bubbles_backend.exception;

public class UserHasJoinedChatroomException extends Exception{
    public UserHasJoinedChatroomException(int userId, int chatroomId){
        super(String.format("User {} has joined chatroom {}!", userId, chatroomId));
    }
}
