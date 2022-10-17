package com.bubbles.bubbles_backend.utils;

import lombok.*;

@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public Result(int code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    static public Result buildSuccessResult(String msg){
        return new Result(0, msg, null);
    }
    static public Result buildSuccessResult(String msg, Object data){
        return new Result(0, msg, data);
    }
    static public Result buildFailResult(String msg){
        return new Result(1, msg, null);
    }
}

