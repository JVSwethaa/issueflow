package com.swethaa.issueflow.service;

import com.swethaa.issueflow.entity.User;
import com.swethaa.issueflow.entity.Role;
import com.swethaa.issueflow.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import com.swethaa.issueflow.dto.RegisterResponse;
import com.swethaa.issueflow.dto.LoginResponse;

import com.swethaa.issueflow.security.JwtService;

@Service
public class UserService {    // Sign Up
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public RegisterResponse register(String name, String email, String password){

        // Check duplicate email
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already registered");
        }

        // Create a user
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); //Plain for now , BCrypt comes in Phase 2
        user.setRole(Role.USER);

        User saved = userRepository.save(user);

        return new RegisterResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole()
        );
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public LoginResponse login(String email, String password){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        //Plain password check
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(token);
    }
}
