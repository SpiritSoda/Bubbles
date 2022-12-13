package com.bubbles.bubbles_backend.exception;

import com.bubbles.bubbles_backend.utils.Result;

public class BubblesException extends Exception{
    public BubblesException(String msg){
        super(msg);
    }
    public Result result(){
        return Result.buildFailResult(-1, this.getMessage());
    }
}
