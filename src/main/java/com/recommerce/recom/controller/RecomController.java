package com.recommerce.recom.controller;

import com.recommerce.recom.entity.LoginRequest;
import com.recommerce.recom.entity.SignupRequest;
import com.recommerce.recom.service.RecomService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/recom")
public class RecomController {

    private final RecomService recomService;

    public RecomController(RecomService recomService) {
        this.recomService = recomService;
    }

    @Operation(summary = "Register new user")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(recomService.signup(request));
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(recomService.login(request));
    }

}
