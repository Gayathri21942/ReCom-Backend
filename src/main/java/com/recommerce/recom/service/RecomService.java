package com.recommerce.recom.service;

import com.recommerce.recom.entity.LoginRequest;
import com.recommerce.recom.entity.RecomEntity;
import com.recommerce.recom.entity.SignupRequest;
import com.recommerce.recom.repo.RecomRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RecomService {

    private final RecomRepo recomRepo;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RecomService(RecomRepo recomRepo) {
        this.recomRepo = recomRepo;
    }

    // SIGNUP
//    public String signup(SignupRequest request) {
//
//        System.out.println("Raw password: " + request.password);
//
//        if (recomRepo.findByUsername(request.username).isPresent()) {
//            throw new RuntimeException("Username already exists");
//        }
//
//        if (recomRepo.findByEmail(request.email).isPresent()) {
//            throw new RuntimeException("Email already exists");
//        }
//
//        RecomEntity user = new RecomEntity();
//        user.setUsername(request.username);
//        user.setEmail(request.email);
//        user.setPassword(passwordEncoder.encode(request.password));
//
//        recomRepo.save(user);
//
//        return "User registered successfully";
//    }

    public String signup(SignupRequest request) {

        System.out.println("Raw password: " + request.password);

// Allowed email domains
        List<String> allowedDomains = Arrays.asList(
                "gmail.com",
                "yahoo.com",
                "outlook.com",
                "hotmail.com"
        );

// Check email format
        if (request.email == null || !request.email.contains("@")) {
            throw new RuntimeException("Invalid email format");
        }

// Extract domain
        String domain = request.email.substring(request.email.indexOf("@") + 1);

// Validate domain
        if (!allowedDomains.contains(domain.toLowerCase())) {
            throw new RuntimeException("Only valid email domains are allowed");
        }

        if (recomRepo.findByUsername(request.username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (recomRepo.findByEmail(request.email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        RecomEntity user = new RecomEntity();
        user.setUsername(request.username);
        user.setEmail(request.email);
        user.setPassword(passwordEncoder.encode(request.password));

        recomRepo.save(user);

        return "User registered successfully";

    }


    // LOGIN
    public String login(LoginRequest request) {

        RecomEntity user = recomRepo.findByUsername(request.username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return "Login successful";
    }

}