package com.liceu.PracticaForum.controller;

import com.google.gson.Gson;
import com.liceu.PracticaForum.form.LoginForm;
import com.liceu.PracticaForum.form.PasswordForm;
import com.liceu.PracticaForum.form.RegisterForm;
import com.liceu.PracticaForum.form.UpdateProfileForm;
import com.liceu.PracticaForum.model.User;
import com.liceu.PracticaForum.service.TokenService;
import com.liceu.PracticaForum.service.UserService;
import com.liceu.PracticaForum.utils.EncriptPass;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        Map<String, Object> loginMap = new HashMap<>();

        if (user == null) {
            userMap.put("message", "Unknown User or Password");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return userMap;
        } else {
            Map<String, Object> permissionMap = userService.getRolePermission(user.getRole());
            userMap = userService.createUserMap(user, permissionMap, userMap);
            userMap.put("message", "User Logged");

            String token = tokenService.newToken(userMap);

            loginMap.put("user", userMap);
            loginMap.put("token", token);
        }
        return loginMap;
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String, Object> signup(@RequestBody RegisterForm registerForm, HttpServletResponse response) {
        String password = encriptPass.encritpPass(registerForm.getPassword());
        User user = new User(registerForm.getName(), registerForm.getEmail(), password, registerForm.getRole());
        boolean userExists = userService.getUserExistsByEmail(registerForm.getEmail());
        Map<String, Object> userMap = new HashMap<>();
        if (userExists) {
            userMap.put("message", "User Already Exists");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return userMap;
        } else {
            userService.addUser(user);
            userService.addMapValues(userMap, user);
        }
        return userMap;
    }

    @GetMapping("/getprofile")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String, Object> getProfile(HttpServletRequest request) {
        String payload = (String) request.getAttribute("payload");
        Gson gson = new Gson();
        Map<String, Object> mapPayload = gson.fromJson(payload, Map.class);
        return mapPayload;
    }

    @PutMapping("/profile")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String, Object> updateProfile(@RequestBody UpdateProfileForm updateProfileForm, HttpServletRequest request) {
        User user = (User) userService.getUser(request);
        userService.updateProfile(updateProfileForm.getName(), updateProfileForm.getEmail(), user);

        user.setEmail(updateProfileForm.getEmail());
        user.setName(updateProfileForm.getName());
        Map<String, Object> userMap = new HashMap<>();
        Map<String, Object> permissionMap = userService.getRolePermission(user.getRole());
        userMap = userService.createUserMap(user, permissionMap, userMap);
        return userMap;
    }

    @PutMapping("/profile/password")
    @CrossOrigin(origins = "http://localhost:8080")
    public boolean updatePassword(@RequestBody PasswordForm passwordForm, HttpServletRequest request) {
        User user = (User) userService.getUser(request);
        String password = encriptPass.encritpPass(passwordForm.getCurrentPassword());
        if (password.equals(user.getPassword())) {
            try {
                if (passwordForm.getNewPassword() != passwordForm.getCurrentPassword()) {
                    userService.updatePassword(encriptPass.encritpPass(passwordForm.getNewPassword()), user);
                    user.setPassword(encriptPass.encritpPass(passwordForm.getNewPassword()));
                }
                return true;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }

        return false;
    }

}
