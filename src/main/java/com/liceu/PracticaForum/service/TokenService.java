package com.liceu.PracticaForum.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.liceu.PracticaForum.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${token.secret}")
    String tokenSecret;
    @Value("${token.expiration}")
    int tokenExpiration;

    public String newToken(User user) {
        String token = JWT.create()
                .withSubject(user.getName())
                .withClaim("user",user.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpiration))
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
        return token;
    }
}
