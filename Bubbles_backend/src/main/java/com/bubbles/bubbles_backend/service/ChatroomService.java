package com.bubbles.bubbles_backend.service;

import com.bubbles.bubbles_backend.repo.ChatroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatroomService {
    private final ChatroomRepository chatroomRepository;
    @Autowired
    ChatroomService(ChatroomRepository chatroomRepository){
        this.chatroomRepository = chatroomRepository;
    }
}
