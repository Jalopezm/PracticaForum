package com.liceu.PracticaForum.repo;

import com.liceu.PracticaForum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Long>{
    List<User> findByEmail(String email);
    List<User> getUserByEmailAndPassword(String email, String password);
}
