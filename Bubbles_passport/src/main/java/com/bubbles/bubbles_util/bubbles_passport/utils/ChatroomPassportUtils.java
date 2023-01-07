package com.bubbles.bubbles_util.bubbles_passport.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bubbles.bubbles_util.bubbles_passport.config.JwtConfig;
import io.jsonwebtoken.ExpiredJwtException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ChatroomPassportUtils {

    public static String createChatroomPassport(int maxUser, JwtConfig jwtConfig){
        String passport = null;
        try {
            Date expire = Date.from(LocalDate.now().plusDays(5).atStartOfDay(ZoneId.systemDefault()).toInstant());
            String token = JWT.create()
                    .withClaim("max_user", maxUser)
                    .withClaim("timestamp", TimeUtils.timestamp())
                    .withClaim("salt", Math.random())
                    .withExpiresAt(expire)
                    .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
            passport = JWT.create()
                    .withClaim("token", token)
                    .withExpiresAt(expire)
                    .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
        } catch (Exception e){
            //e.printStackTrace();
        }
        return passport;
    }
    private static String getToken(String token, JwtConfig jwtConfig){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtConfig.getSecret())).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims().get("token").asString();
    }
    public static int getMaxUser(String token, JwtConfig jwtConfig){
        String innerToken = getToken(token, jwtConfig);
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtConfig.getSecret())).build();
        DecodedJWT jwt = verifier.verify(innerToken);
        return jwt.getClaims().get("max_user").asInt();
    }
    public static boolean verify(String token, JwtConfig jwtConfig){
        String innerToken = getToken(token, jwtConfig);
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtConfig.getSecret())).build();
        try {
            DecodedJWT jwt = verifier.verify(innerToken);
        }
        catch (ExpiredJwtException e){
            throw e;
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
