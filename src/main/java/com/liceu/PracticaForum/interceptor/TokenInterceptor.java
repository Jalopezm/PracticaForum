package com.liceu.PracticaForum.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;

import com.liceu.PracticaForum.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            try {
                String token = authHeader.replace("Bearer ", "");
                String payload = tokenService.getPayload(token);
                byte[] decoded = Base64.getDecoder().decode(payload);
                String decodedStr = new String(decoded, StandardCharsets.UTF_8);
                request.setAttribute("payload", decodedStr);
            } catch (SignatureVerificationException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
        return true;
    }
}
