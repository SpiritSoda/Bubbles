package com.bubbles.bubbles_backend.controller;

import com.bubbles.bubbles_backend.service.ChatroomService;
import com.bubbles.bubbles_backend.service.MessageService;
import com.bubbles.bubbles_backend.service.UserService;
import com.bubbles.bubbles_backend.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final UserService userService;
    private final ChatroomService chatroomService;
    @Autowired
    ChatController(SimpMessagingTemplate simpMessagingTemplate, MessageService messageService, UserService userService, ChatroomService chatroomService){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
        this.userService = userService;
        this.chatroomService = chatroomService;
    }
}
