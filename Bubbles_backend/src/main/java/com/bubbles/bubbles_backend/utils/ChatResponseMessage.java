package com.bubbles.bubbles_backend.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatResponseMessage {
    private int serial;
    /*
    * 0: initialize info
    * 1: message synchronize
    * 2: update online users
    *
    *
    *
    */
    private int type;
    private Object data;
}
