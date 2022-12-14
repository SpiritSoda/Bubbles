package com.bubbles.bubbles_backend.dto;

import lombok.Data;

@Data
public class MessageQueryDTO {
    private int startId;
    private int chatroomId;
    private int cnt;
}
