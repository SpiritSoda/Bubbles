package com.bubbles.bubbles_backend.utils;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bubbles.bubbles_backend.config.JwtConfig;
import com.bubbles.bubbles_backend.entity.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    public static String sign(User user, JwtConfig jwtConfig){
        String token = null;
        try {
            Date expire = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
            token = JWT.create()
                    .withClaim("username", user.getUsername())
                    .withClaim("user_id", user.getUserId())
                    // .withClaim("user_tags", user.getInterestTags())
                    // 这里注释掉，是否传List会拖慢速度？
                    .withExpiresAt(expire)
                    .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
        } catch (Exception e){
            //e.printStackTrace();
        }
        return token;
    }
    public static String createChatroomPassport(int maxUser, JwtConfig jwtConfig){
        String token = null;
        try {
            Date expire = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
            token = JWT.create()
                    .withClaim("max_user", maxUser)
                    .withClaim("timestamp", System.currentTimeMillis() / 1000l)
                    .withClaim("salt", Math.random())
                    // .withClaim("user_tags", user.getInterestTags())
                    // 这里注释掉，是否传List会拖慢速度？
                    .withExpiresAt(expire)
                    .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
        } catch (Exception e){
            //e.printStackTrace();
        }
        return token;
    }

    public static Map<String, Claim> verify(String token, JwtConfig jwtConfig){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtConfig.getSecret())).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    public static int getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("user_id").asInt();
        } catch (Exception e) {
            return -1;
        }
    }

}
