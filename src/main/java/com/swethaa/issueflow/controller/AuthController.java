package com.swethaa.issueflow.controller;

import com.swethaa.issueflow.entity.User;
import com.swethaa.issueflow.service.UserService;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.swethaa.issueflow.dto.RegisterRequest;
import com.swethaa.issueflow.dto.LoginRequest;

import com.swethaa.issueflow.security.JwtService;
import com.swethaa.issueflow.dto.LoginResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request){
        User created = userService.register(request.getName(), request.getEmail(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        User user = userService.login(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
