package com.example.cage_api.controller;


import com.example.cage_api.model.Users;
import com.example.cage_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class Controller {
    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable String userId){
        return userService.findById(Integer.parseInt(userId));
    }

    @GetMapping("/All")
    public List<Users> getAllUser(@RequestBody Users user){
        return userService.fingAllby();
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody Users user){
        return userService.save(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUserStatus(@PathVariable String userId){
        return userService.updateUser(Integer.parseInt(userId));
    }
}
