package com.bubbles.bubbles_backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bubbles.bubbles_backend.config.JwtConfig;
import io.jsonwebtoken.ExpiredJwtException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InviteTokenUtils {
    public static String generateInviteToken(int id, JwtConfig jwtConfig){
        String token = null;
        try {
            Date expire = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            token = JWT.create()
                    .withClaim("id", id)
                    .withClaim("timestamp", TimeUtils.timestamp())
                    .withClaim("salt", Math.random())
                    .withExpiresAt(expire)
                    .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
        } catch (Exception e){
            //e.printStackTrace();
        }
        return token;
    }
    public static int verifyInviteToken(String token, JwtConfig jwtConfig){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtConfig.getSecret())).build();
        try{
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims().get("id").asInt();
        }
        catch (ExpiredJwtException e){
            throw e;
        }
        catch (Exception e){
            return 0;
        }
    }
}
