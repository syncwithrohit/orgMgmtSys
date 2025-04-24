package com.org.controller;

import com.org.config.SecurityConfig;
import com.org.entity.AuthReq;
import com.org.entity.User;
import com.org.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/reg")
    public ResponseEntity<String> regUser (@RequestBody User user){
        if (userRepo.findByUsername(user.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("User already exist. Kindly login.");
        }
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> handleLogin (@RequestBody AuthReq authReq) {
        Authentication a = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authReq.getUsername(),authReq.getPassword()));

        if (a.isAuthenticated()) {
            return ResponseEntity.ok("User logged in successfully.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }
}
