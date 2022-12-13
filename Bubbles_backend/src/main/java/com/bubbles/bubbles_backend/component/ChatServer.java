package com.bubbles.bubbles_backend.component;

import com.bubbles.bubbles_backend.entity.User;
import com.bubbles.bubbles_backend.exception.TokenNotFoundException;
import com.bubbles.bubbles_backend.service.UserService;
import com.bubbles.bubbles_backend.utils.ChatRequestMessage;
import com.bubbles.bubbles_backend.utils.ChatResponseMessage;
import com.bubbles.bubbles_backend.utils.JwtUtils;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import javax.websocket.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
//@ServerEndpoint(value = "chat/{token}",configurator = WebSocketConfig.class)
public class ChatServer implements WebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(ChatServer.class);
    public static final Map<WebSocketSession, Integer> sessionMap = new ConcurrentHashMap<>();

    private final UserService userService;
    @Autowired
    public ChatServer(UserService userService){
        this.userService = userService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        String token = session.getHandshakeHeaders().get("token").get(0);
        String[] params = session.getUri().getQuery().split("&");
        if(params.length == 0)
            throw new TokenNotFoundException("Token not found ...");
        String token = params[0].split("=")[1];

        int userId = JwtUtils.getUserId(token);
        User user = this.userService.findById(userId);
        log.info("User " + user.getUsername() + "-" + userId + " enter the chatroom ...");
        sessionMap.put(session, userId);

    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        User user = this.userService.findById(sessionMap.get(session));
        log.info("User " + user.getUsername() + "-" + sessionMap.get(session) + " exit the chatroom ...");
        sessionMap.remove(session);
    }
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String msg = message.getPayload().toString();
        log.info("Message from user " + sessionMap.get(session) + ": " + msg);
        Gson gson = new Gson();
        ChatRequestMessage request = gson.fromJson(msg, ChatRequestMessage.class);
        if(request.getType() == 0){
            User user = userService.findById(sessionMap.get(session));
            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            data.put("userId", user.getUserId());
            data.put("avatar", user.getAvatar());
            data.put("username", user.getUsername());
            data.put("online", sessionMap.values());
            session.sendMessage(new TextMessage(gson.toJson(new ChatResponseMessage(request.getSerial(), 0, data))));
        }
    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
    }
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void sendMessage(String message, Session toSession) {
    }
    private void sendAllMessage(String message) {
    }
}
