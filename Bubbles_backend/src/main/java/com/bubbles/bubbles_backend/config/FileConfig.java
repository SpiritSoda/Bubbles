package com.bubbles.bubbles_backend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class FileConfig {
    @Value("${bubbles.chatroom.maxFileSize}")
    private int maxFileSize;
}
