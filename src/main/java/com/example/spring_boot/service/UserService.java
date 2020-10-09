package com.example.spring_boot.service;

import com.example.spring_boot.model.Role;
import com.example.spring_boot.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    boolean save(User user, String roleListEdit);
    boolean edit(User user, String roleListEdit);
    void delete(Long id);
    User findUserById(Long id);
    Role findRoleById(Long id);
    User findUserByUsername(String Username);
}
