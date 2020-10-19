package com.example.spring_boot.controller;

import com.example.spring_boot.model.User;
import com.example.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/allUsers")
    public ResponseEntity<List<User>> listUsers(){
        final List<User> users = userService.findAll();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/findOne/{id}")
    public ResponseEntity<User> findOne(@PathVariable(name = "id") Long id){
        final User user = userService.findUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<User> addUser(@RequestBody @Valid User user, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }

    }

    @PutMapping(value = "/edit")
    public ResponseEntity<User> editUser(@RequestBody @Valid User user, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            final boolean edited = userService.edit(user);

            return edited
                    ? new ResponseEntity<>(user, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@RequestBody long id){
        final boolean deleted = userService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @PostMapping(value = "/relevationUsername")
    public Boolean relevationUsername(@RequestBody List<String> emails){
        if(userService.findUserByUsername(emails.get(1)) == null || emails.get(1).equals(emails.get(0))){
            return false;
        }
        return true;
    }

}
