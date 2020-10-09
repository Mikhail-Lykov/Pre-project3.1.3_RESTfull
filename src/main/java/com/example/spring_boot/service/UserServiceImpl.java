package com.example.spring_boot.service;

import com.example.spring_boot.dao.RoleDao;
import com.example.spring_boot.dao.UserDao;
import com.example.spring_boot.model.Role;
import com.example.spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public boolean save(User user, String roleListEdit) {

        if(userDao.getUserByName(user.getUsername()) != null){
            return false;
        }

        Set<Role> roles = new HashSet<>();
        List<String> list = Arrays.asList(roleListEdit.split(","));
        if(list.contains("2")){
            roles.add(roleDao.getOne(2L));
        }
        if(list.contains("1") || list.contains("")){
            roles.add(roleDao.getOne(1L));
        }

        user.setRoles(roles);
        userDao.save(user);
        return true;
    }

    @Override
    public boolean edit(User user, String roleListEdit) {

        if(userDao.getUserByName(user.getUsername()) == null ||
                userDao.getOne(user.getId()).getUsername().equals(user.getUsername())){

            user.setRoles(userDao.getOne(user.getId()).getRoles());

            List<String> list = Arrays.asList(roleListEdit.split(","));
            for (String a : list) {
                System.out.println("el: " + a);
            }
            if(list.contains("2")){
                user.getRoles().add(roleDao.getOne(2L));
            }
            else{
                user.getRoles().remove(roleDao.getOne(2L));
            }

            if(list.contains("1") || list.contains("")){
                user.getRoles().add(roleDao.getOne(1L));
            }
            else{
                user.getRoles().remove(roleDao.getOne(1L));
            }
            userDao.save(user);
            return true;
        }

        return false;
    }

    @Override
    public void delete(Long id) {
        userDao.deleteById(id);
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
