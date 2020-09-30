package com.example.spring_boot.controller;

import com.example.spring_boot.model.User;
import com.example.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String printWelcome(){
        return "index";
    }

    @PostMapping(value = "/")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            String message = result.getFieldError().getDefaultMessage();
            model.addAttribute("message", message);
            return "/new";
        }

        if(!userService.save(user)){
            model.addAttribute("message", "E-mail already exists");
            return "/new";
        }

        return "redirect:/login";
    }

    @GetMapping(value = "/new")
    public String newUser(@ModelAttribute("user") User user){
        return "new";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }
}
