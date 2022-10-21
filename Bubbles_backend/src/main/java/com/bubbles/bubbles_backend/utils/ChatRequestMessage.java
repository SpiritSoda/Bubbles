package com.bubbles.bubbles_backend.utils;

import lombok.Data;

@Data
public class ChatRequestMessage {
    private int serial;
    /*
    * 0: initialize session
    * 1: send message
    * 2: query online users
    *
    */
    private int type;
    private Object data;
}
