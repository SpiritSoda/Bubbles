package com.bubbles.bubbles_backend.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private int messageId;

    private int session;
    private int type;
    private String content;
    private int senderId;
    private long timestamp;
}
