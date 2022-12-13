package com.bubbles.bubbles_backend.component;

import com.bubbles.bubbles_backend.service.UserService;
import com.bubbles.bubbles_backend.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Component
@Slf4j
public class ConnectListener implements ApplicationListener<SessionConnectEvent> {
    private final ChatroomManager chatroomManager;
    private final UserService userService;

    @Autowired
    public ConnectListener(ChatroomManager chatroomManager, UserService userService) {
        this.chatroomManager = chatroomManager;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String token = accessor.getLogin();
        try {
            chatroomManager.online(userService.findByToken(token).getUserId(), sessionId);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
