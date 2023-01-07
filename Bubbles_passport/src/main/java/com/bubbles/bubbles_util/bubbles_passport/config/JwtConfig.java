package com.bubbles.bubbles_util.bubbles_passport.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;
}
