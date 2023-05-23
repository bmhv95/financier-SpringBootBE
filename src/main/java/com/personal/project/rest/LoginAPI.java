package com.personal.project.rest;

import com.personal.project.service.DTO.JwtLoginRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public interface LoginAPI {
    @PostMapping("/signin")
    ResponseEntity<?> authenticateAccount(@Valid @RequestBody JwtLoginRequest jwtLoginRequest);

    @PostMapping("/signup")
    ResponseEntity<?> registerAccount(@Valid @RequestBody JwtLoginRequest jwtLoginRequest);
}
