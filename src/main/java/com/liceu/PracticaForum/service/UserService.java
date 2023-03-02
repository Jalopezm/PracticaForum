package com.liceu.PracticaForum.service;

import com.google.gson.Gson;
import com.liceu.PracticaForum.model.*;
import com.liceu.PracticaForum.repo.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    TokenService tokenService;
    @Autowired
    CategoryService categoryService;

    public void addUser(User user) {
        userRepo.save(user);
    }


    public boolean getUserExistsByEmail(String email) {
        List<User> userList = userRepo.findByEmail(email);
        return userList.size() > 0;
    }

    public User logUser(String email, String password) {
        List<User> userList = userRepo.getUserByEmailAndPassword(email, password);
        if (userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    public Map<String, Object> getRolePermission(String role) {
        Map<String, Object> rootMap = new HashMap<>();
        String[] rootArrayPermission = new String[]{
                "own_topics:write",
                "own_topics:delete",
                "own_replies:write",
                "own_replies:delete",
                "categories:write",
                "categories:delete"
        };
        rootMap.put("root", rootArrayPermission);
        rootMap.put("categories", categoryService.createCategoryMapPermisions());
        switch (role) {
            case "admin" -> {
                return rootMap;
            }
            case "mod" -> {
                return null;
            }
            case "user" -> {
                return null;
            }
        }
        return null;
    }

    public Map<String, Object> createUserMap(User user, Map<String, Object> permissionMap, Map<String, Object> userMap) {

        userMap.put("role", user.getRole());
        userMap.put("_id", String.valueOf(user.getId()));
        userMap.put("id", String.valueOf(user.getId()));
        userMap.put("email", user.getEmail());
        userMap.put("name", user.getName());
        userMap.put("permissions", permissionMap);
        userMap.put("avatarUrl", user.getUserAvatar());
        return userMap;
    }

    public Map<String, Object> getCurrentUserMap(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.replace("Bearer ", "");
        String payload = tokenService.getPayload(token);
        byte[] decoded = Base64.getDecoder().decode(payload);
        String decodedStr = new String(decoded, StandardCharsets.UTF_8);

        Gson gson  =  new Gson();
        Map<String, Object> userMap = gson.fromJson(decodedStr, Map.class);
        return userMap;
    }

    public Object getUser(HttpServletRequest request) {
        Map<String, Object> userMap = getCurrentUserMap(request);
        User user = userRepo.getUserByEmail((String) userMap.get("email"));
        return user;
    }
}
