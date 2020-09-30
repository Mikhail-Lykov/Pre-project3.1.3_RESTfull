package com.example.spring_boot.dao;

import com.example.spring_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    default User getUserByName(String username) {
        for(User user : findAll()){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
}
