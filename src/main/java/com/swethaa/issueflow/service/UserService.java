package com.swethaa.issueflow.service;

import com.swethaa.issueflow.entity.User;
import com.swethaa.issueflow.entity.Role;
import com.swethaa.issueflow.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class UserService {    // Sign Up
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String name, String email, String password){

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

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User login(String email, String password){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        //Plain password check
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Invalid email or password");
        }
        return user;
    }
}
