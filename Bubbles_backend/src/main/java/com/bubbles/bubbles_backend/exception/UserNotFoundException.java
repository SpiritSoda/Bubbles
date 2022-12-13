package com.bubbles.bubbles_backend.exception;

import com.bubbles.bubbles_backend.utils.Result;

public class UserNotFoundException extends BubblesException {
    public UserNotFoundException(String username) {
        super(String.format("Username `%s` not found!", username));
    }
    public UserNotFoundException(int userId) {
        super(String.format("User ID `%d` not found!", userId));
    }

    @Override
    public Result result(){
        return Result.buildFailResult(ErrorCode.USER_NOT_FOUND, this.getMessage());
    }
}
