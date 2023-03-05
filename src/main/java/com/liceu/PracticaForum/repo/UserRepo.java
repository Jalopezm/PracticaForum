package com.liceu.PracticaForum.repo;

import com.liceu.PracticaForum.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Long>{
    List<User> findByEmail(String email);
    List<User> getUserByEmailAndPassword(String email, String password);
    User getUserByEmail(String userName);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name=:name,u.email=:email WHERE u.id=:id")
    void updateProfile(String name, String email, long id);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password=:password WHERE u.id=:id")
    void updatePassword(String password, long id);
}
