package com.example.spring_boot.service;

import com.example.spring_boot.dao.RoleDao;
import com.example.spring_boot.dao.UserDao;
import com.example.spring_boot.model.Role;
import com.example.spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public boolean save(User user) {

        if(userDao.getUserByName(user.getUsername()) != null){
            return false;
        }else{
            userDao.save(user);
            return true;
        }

    }

    @Override
    public boolean edit(User user) {

        if(userDao.getUserByName(user.getUsername()) == null ||
                userDao.getOne(user.getId()).getUsername().equals(user.getUsername())){
            userDao.save(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        try{
            userDao.deleteById(id);
            return true;
        } catch(Exception e){
            System.err.println(e);
            return false;
        }

    }

    @Override
    public User findUserById(Long id) {
        return userDao.findById(id).get();
    }

    @Override
    public Role findRoleById(Long id) {
        return roleDao.getOne(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.getUserByName(username);
    }
}
