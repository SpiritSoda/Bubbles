package com.bubbles.bubbles_backend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BubblesConfig {
    @Value("${bubbles.chatroom.inviteTokenExceedTime}")
    private long inviteTokenExceedTime;
    @Value("${bubbles.chatroom.maxTextMessageLength}")
    private int maxTextMessageLength;
}
