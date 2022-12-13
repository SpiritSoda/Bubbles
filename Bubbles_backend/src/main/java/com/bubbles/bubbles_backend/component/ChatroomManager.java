package com.bubbles.bubbles_backend.component;

import com.bubbles.bubbles_backend.entity.Chatroom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ChatroomManager {
    private Set<Integer> online = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private ConcurrentHashMap<String, Integer> sessions = new ConcurrentHashMap<>();
    public void online(int id, String sessionId){
        online.add(id);
        sessions.put(sessionId, id);
//        log.info("login: " + id);
    }
    public void offline(String sessionId){
        Integer id = sessions.remove(sessionId);
        if(id == null)
            return ;
        online.remove(id);
//        log.info("logout: " + id);
    }
    public int chatroomOnline(Chatroom chatroom){
//        log.info("calculating: " + chatroom.getChatroomId());
        return (int) chatroom.getUsers().stream().filter(user -> online.contains(user.getUserId())).count();
    }
}
