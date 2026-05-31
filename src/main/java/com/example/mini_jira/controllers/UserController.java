package com.example.mini_jira.controllers;

import com.example.mini_jira.Services.JWTService;
import com.example.mini_jira.Services.UserService;
import com.example.mini_jira.classes.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService UserService;
    private final JWTService jwtService;

    public UserController(UserService userService, JWTService jwtService) {
        this.UserService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return UserService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return UserService.getUserById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        User loggedUser = UserService.login(user.getUsername(), user.getPassword());
        String token = jwtService.generateToken(loggedUser);
        return ResponseEntity.ok(token);
    }
}
