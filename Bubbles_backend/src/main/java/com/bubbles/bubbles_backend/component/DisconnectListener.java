package com.bubbles.bubbles_backend.component;

import com.bubbles.bubbles_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class DisconnectListener implements ApplicationListener<SessionDisconnectEvent> {
    private final ChatroomManager chatroomManager;
    private final UserService userService;

    @Autowired
    public DisconnectListener(ChatroomManager chatroomManager, UserService userService) {
        this.chatroomManager = chatroomManager;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        chatroomManager.offline(sessionId);
    }
}
