package com.bubbles.bubbles_backend.config;

import com.bubbles.bubbles_backend.interceptor.ChatInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class STOMPConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtConfig jwtConfig;
    @Autowired
    STOMPConfig(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //endPoint 注册协议节点,并映射指定的URl点对点-用
        //注册一个名字为"/endpointSocket" 的endpoint,并指定 SockJS协议。
        //允许使用socketJs方式访问，访问点为webSocketServer，允许跨域
        //连接前缀
        registry.addEndpoint("/ems_course_session")
                .setAllowedOrigins("*")  // 跨域处理
                .addInterceptors(createChatInterceptor())
                .withSockJS();  //支持socketJs
    }
    @Bean
    public ChatInterceptor createChatInterceptor(){
        return new ChatInterceptor(jwtConfig);
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/api/chatroom", "/user");

        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        config.setApplicationDestinationPrefixes("/api/chatroom");

        // 修改convertAndSendToUser方法前缀
        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），
        // 不设置的话，默认也是/user/
        // config.setUserDestinationPrefix ("/user");
    }
}
