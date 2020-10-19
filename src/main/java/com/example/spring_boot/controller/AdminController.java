package com.example.spring_boot.controller;

import com.example.spring_boot.model.User;
import com.example.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/admin")
    public String showAllUsers(@AuthenticationPrincipal User auth, @ModelAttribute("user") User user, ModelMap model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("auth", auth);
        return "admin";
    }

    @GetMapping(value = "/user")
    public String userInfo(@AuthenticationPrincipal User auth, ModelMap model){
        model.addAttribute("auth", auth);
        return "user";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }


}
