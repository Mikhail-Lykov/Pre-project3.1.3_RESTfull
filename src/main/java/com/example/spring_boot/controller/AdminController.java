package com.example.spring_boot.controller;

import com.example.spring_boot.model.User;
import com.example.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/edit")
    public String editUser(@ModelAttribute("roleListEdit") String roleList,
                           @ModelAttribute("user") @Valid User user){

        userService.edit(user, roleList);
        return "redirect:/admin";
    }

    @PostMapping(value = "/add")
    public String addUser(@ModelAttribute("roleListAdd") String roleList,
                          @ModelAttribute("user") @Valid User user){
        userService.save(user, roleList);
        return "redirect:/admin";
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

    @GetMapping(value = "/findOne")
    @ResponseBody
    public User findOne(Long id){
        return userService.findUserById(id);
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@ModelAttribute("user") User user){
        userService.delete(user.getId());
        return "redirect:/admin";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }


    @PostMapping(value = "/relevationUsername")
    @ResponseBody
    public Boolean relevationUsername(@ModelAttribute("newuser") String newuser, @ModelAttribute("olduser") String olduser){
        if(userService.findUserByUsername(newuser) == null || newuser.equals(olduser)){
            return false;
        }
        return true;
    }
}
