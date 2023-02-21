package com.liceu.PracticaForum.service;

import com.liceu.PracticaForum.model.*;
import com.liceu.PracticaForum.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

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

    public Map<String, Object> createUserMap(User user,Map<String, Object> permissionMap,  Map<String, Object> userMap) {
        userMap.put("role", user.getRole());
        userMap.put("_id", String.valueOf(user.getId()));
        userMap.put("email", user.getEmail());
        userMap.put("name", user.getName());
        userMap.put("permissions", permissionMap);
        return userMap;
    }

    public User getUser(String userName) {
       return userRepo.getUserByEmail(userName);
    }
}
