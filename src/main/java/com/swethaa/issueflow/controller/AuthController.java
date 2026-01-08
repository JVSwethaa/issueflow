package com.swethaa.issueflow.controller;

import com.swethaa.issueflow.entity.User;
import com.swethaa.issueflow.service.UserService;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.swethaa.issueflow.dto.RegisterRequest;
import com.swethaa.issueflow.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request){
        User created = userService.register(request.getName(), request.getEmail(), request.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request){
        User user = userService.login(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(user);
    }
}
