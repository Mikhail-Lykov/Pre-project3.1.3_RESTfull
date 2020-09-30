package com.example.spring_boot.controller;

import com.example.spring_boot.model.Role;
import com.example.spring_boot.model.User;
import com.example.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/admin")
    public String editUser(@ModelAttribute("adminRole") String adminRole,
                           @ModelAttribute("user") @Valid User user, BindingResult result, ModelMap model){

        Boolean b = false;
        if(adminRole.equals("1")){
            b = true;
        }

        if(result.hasErrors()){
            String message = result.getFieldError().getDefaultMessage();
            model.addAttribute("message", message);
            model.addAttribute("adminRole", b);
            return "edit";
        }

        if(!userService.edit(user, adminRole)){
            model.addAttribute("message", "Username already exists");
            model.addAttribute("adminRole", b);
            return "edit";
        }

        return "redirect:/admin";
    }


    @GetMapping(value = "/admin")
    public String showAllUsers(ModelMap model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }


    @GetMapping(value = "/admin/edit{id}")
    public String showUser(@PathVariable("id") long id, ModelMap model){
        User user = userService.findUserById(id);

        Set<String> set = new HashSet<>();
        for(Role u : user.getRoles()){
            set.add(u.getRole());
        }

        Boolean b = false;
        if(set.contains("ROLE_ADMIN")){
            b = true;
        }

        model.addAttribute("user", user);
        model.addAttribute("adminRole", b);
        return "edit";
    }


    @PostMapping(value = "/admin/{id}")
    public String deleteUser(@PathVariable("id") long id){
        userService.delete(id);
        return "redirect:/admin";
    }
}
