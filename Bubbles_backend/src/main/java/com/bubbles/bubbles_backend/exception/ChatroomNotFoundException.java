package com.bubbles.bubbles_backend.exception;

public class ChatroomNotFoundException extends Exception {
    public ChatroomNotFoundException(int chatroomId) {
        super(String.format("Chatroom ID `%d` not found!", chatroomId));
    }
}
