package com.liceu.PracticaForum.controller;

import com.liceu.PracticaForum.form.LoginForm;
import com.liceu.PracticaForum.form.RegisterForm;
import com.liceu.PracticaForum.model.User;
import com.liceu.PracticaForum.service.TokenService;
import com.liceu.PracticaForum.service.UserService;
import com.liceu.PracticaForum.utils.EncriptPass;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    EncriptPass encriptPass;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String, Object> login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        String password = encriptPass.encritpPass(loginForm.getPassword());
        User user = userService.logUser(loginForm.getEmail(), password);
        Map<String, Object> userMap = new HashMap<>();
        if (user == null) {
            userMap.put("message", "Unknown User or Password");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return userMap;
        } else {
            Map<String, Object> permissionMap = userService.getRolePermission(user.getRole());

            userMap = userService.createUserMap(user, permissionMap, userMap);
//            userMap.put("categories",categoriesMap);

            String token = tokenService.newToken(userMap);

            userMap.put("token", token);
            userMap.put("message", "User Logged");
        }
        return userMap;
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String, String> signup(@RequestBody RegisterForm registerForm, HttpServletResponse response) {
        String password = encriptPass.encritpPass(registerForm.getPassword());
        User user = new User(registerForm.getName(), registerForm.getEmail(), password, registerForm.getRole());
        boolean userExists = userService.getUserExistsByEmail(registerForm.getEmail());
        Map<String, String> userMap = new HashMap<>();
        if (userExists) {
            userMap.put("message", "User Already Exists");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return userMap;
        } else {
            userService.addUser(user);
            userMap.put("name", user.getName());
            userMap.put("email", user.getEmail());
            userMap.put("password", user.getPassword());
            userMap.put("message", "User Created");
//            userMap.put("moderateCategory",);
        }
        return userMap;
    }

    @GetMapping("/getprofile")
    @CrossOrigin(origins = "http://localhost:8080")
    public String getProfile(HttpServletRequest request) {
        Map<String, Object> userMap = new HashMap<>();
        String payload = (String) request.getAttribute("payload");
//        Map<String, Object> permissionMap = userService.getRolePermission(user.getRole());
//
//        userMap = userService.createUserMap(user, permissionMap, userMap);
////            userMap.put("categories",categoriesMap);
//
//        String token = tokenService.newToken(userMap);
//
//        userMap.put("token", token);
//        userMap.put("message", "User Logged");

        return payload;
    }


}
