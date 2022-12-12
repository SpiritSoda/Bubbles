package com.bubbles.bubbles_backend.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String username) {
        super(String.format("Username `%s` not found!", username));
    }
    public UserNotFoundException(int userId) {
        super(String.format("User ID `%d` not found!", userId));
    }
}
