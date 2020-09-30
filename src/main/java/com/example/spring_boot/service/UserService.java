package com.example.spring_boot.service;

import com.example.spring_boot.model.Role;
import com.example.spring_boot.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    boolean save(User user);
    boolean edit(User user, String roleAdmin);
    void delete(Long id);
    User findUserById(Long id);
    Role findRoleById(Long id);
}
