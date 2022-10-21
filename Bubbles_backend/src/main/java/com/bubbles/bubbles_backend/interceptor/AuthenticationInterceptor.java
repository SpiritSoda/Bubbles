package com.bubbles.bubbles_backend.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.bubbles.bubbles_backend.config.JwtConfig;
import com.bubbles.bubbles_backend.exception.NoTokenException;
import com.bubbles.bubbles_backend.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Autowired
    JwtConfig jwtConfig;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        if (token == null){
            throw new NoTokenException("Token not found ...");
        }

        // logger.info("认证 {}", token);
        Map<String, Claim> verify = JwtUtils.verify(token, jwtConfig);
        return true;
    }
}
