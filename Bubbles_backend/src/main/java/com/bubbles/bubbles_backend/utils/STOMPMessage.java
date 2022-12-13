package com.bubbles.bubbles_backend.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class STOMPMessage {
    private int type;
    private Object data;

    public static STOMPMessage buildMessage(int type, Object o){
        return new STOMPMessage(type, o);
    }
}