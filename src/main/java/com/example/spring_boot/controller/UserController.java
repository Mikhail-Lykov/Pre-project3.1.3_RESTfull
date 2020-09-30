package com.example.spring_boot.controller;

import com.example.spring_boot.model.User;
import com.example.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String userInfo(@AuthenticationPrincipal User user, ModelMap model){
        model.addAttribute("user", userService.findUserById(user.getId()));
        return "user";
    }

    @GetMapping(value = "/user/{id}")
    public String showUser(@PathVariable("id") long id, ModelMap model){
        model.addAttribute("user", userService.findUserById(id));
        return "user";
    }


}
