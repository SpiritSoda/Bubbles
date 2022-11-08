package com.bubbles.bubbles_backend.interceptor;

import com.bubbles.bubbles_backend.config.JwtConfig;
import com.bubbles.bubbles_backend.exception.NoTokenException;
import com.bubbles.bubbles_backend.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
public class ChatInterceptor implements HandshakeInterceptor {
    private final JwtConfig jwtConfig;
    @Autowired
    public ChatInterceptor(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) {
//        String token = serverHttpRequest.getHeaders().get("token").get(0);
        String[] params = serverHttpRequest.getURI().getQuery().split("&");
        if(params.length == 0)
                return false;
        String token = params[0].split("=")[1];

//         log.info("认证 {}", token);
        try{
            if (token == null){
                throw new NoTokenException("Token not found ...");
            }
            JwtUtils.verify(token, jwtConfig);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
