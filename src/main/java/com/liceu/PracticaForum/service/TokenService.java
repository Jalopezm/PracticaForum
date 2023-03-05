package com.liceu.PracticaForum.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class TokenService {
    @Value("${token.secret}")
    String tokenSecret;
    @Value("${token.expiration}")
    int tokenExpiration;

    public String newToken(Map<String, Object> userMap) {
        String token = JWT.create()
                .withPayload(userMap)
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpiration))
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
        return token;
    }

    public String getPayload(String token) {
        if(token.equals("null"))return null;
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                .build()
                .verify(token);
        String payload = decodedJWT.getPayload();


        return payload;
    }
}
