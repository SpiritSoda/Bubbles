package com.bubbles.bubbles_backend.component;

import com.bubbles.bubbles_backend.entity.Chatroom;
import com.bubbles.bubbles_backend.utils.STOMPMessageType;
import com.bubbles.bubbles_backend.utils.STOMPMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ChatroomManager {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private ConcurrentHashMap<String, Integer> sessions = new ConcurrentHashMap<>();

    @Autowired
    public ChatroomManager(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void online(int id, String sessionId){
        if(sessions.containsValue(id) && !sessions.contains(sessionId)){
//            log.info("repeat: " + id + " " + sessionId);
            simpMessagingTemplate.convertAndSendToUser(String.valueOf(id), "/receive", STOMPMessage.buildMessage(STOMPMessageType.RE_LOGIN_MESSAGE, 0));

        }

        sessions.put(sessionId, id);
//        log.info("login: " + id + " " + sessionId);
    }
    public void offline(String sessionId){
        Integer id = sessions.remove(sessionId);
//        log.info("logout: " + id + " " + sessionId);
    }
    public int chatroomOnlineCnt(Chatroom chatroom){
//        log.info("calculating: " + chatroom.getChatroomId());
        return (int) chatroom.getUsers().stream().filter(user -> sessions.containsValue(user.getUserId())).count();
    }
    public List<Integer> chatroomOnline(Chatroom chatroom){
//        log.info("calculating: " + chatroom.getChatroomId());
        return chatroom.getUsers().stream().filter(user -> sessions.containsValue(user.getUserId())).map(user -> user.getUserId()).collect(Collectors.toList());
    }
}
