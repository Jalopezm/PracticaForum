package com.liceu.PracticaForum.service;

import com.liceu.PracticaForum.model.*;
import com.liceu.PracticaForum.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<User> userList = userRepo.getUserByEmailAndPassword(email,password);
        if (userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }
}
