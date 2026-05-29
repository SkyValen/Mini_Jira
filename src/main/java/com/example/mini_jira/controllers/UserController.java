package com.example.mini_jira.controllers;

import com.example.mini_jira.Services.UserService;
import com.example.mini_jira.classes.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService userService) { this.service = userService; }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }
}
