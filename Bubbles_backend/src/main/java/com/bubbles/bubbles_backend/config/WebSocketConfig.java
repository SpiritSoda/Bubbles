package com.bubbles.bubbles_backend.config;

import com.bubbles.bubbles_backend.component.ChatServer;
import com.bubbles.bubbles_backend.interceptor.ChatInterceptor;
import com.bubbles.bubbles_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


//@Slf4j
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//    private final JwtConfig jwtConfig;
//    private final UserService userService;
//    @Autowired
//    public WebSocketConfig(JwtConfig jwtConfig, UserService userService){
//        this.jwtConfig = jwtConfig;
//        this.userService = userService;
//    }
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
//        webSocketHandlerRegistry.addHandler(createChatServer(), "/chat")
//                .addInterceptors(getChatInterceptor())
//                .setAllowedOrigins("*");
//    }
//    @Bean
//    public ChatInterceptor getChatInterceptor(){
//        return new ChatInterceptor(jwtConfig);
//    }
//    @Bean
//    public ChatServer createChatServer(){
//        return new ChatServer(this.userService);
//    }
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
//}
