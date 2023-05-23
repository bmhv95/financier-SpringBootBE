package com.personal.project.rest.controller;

import com.personal.project.rest.AdminAPI;
import com.personal.project.service.AccountService;
import com.personal.project.service.DTO.AccountDTO;
import com.personal.project.service.DTO.EnvelopeDTO;
import com.personal.project.service.DTO.GoalDTO;
import com.personal.project.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminAPI {
    private final JwtUtils jwtUtils;
    private final AccountService accountService;
    @Override
    public ResponseEntity<List<AccountDTO>> getOldAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
    @Override
    public ResponseEntity<List<EnvelopeDTO>> getAllEnvelopes() {
        return null;
    }

    @Override
    public ResponseEntity<List<GoalDTO>> getAllGoals() {
        return null;
    }

    @Override
    public ResponseEntity<String> testToken(String token) {
        return ResponseEntity.ok(jwtUtils.getEmailFromJwtToken(token));
    }
}
