package com.personal.project.rest;

import com.personal.project.service.DTO.AccountDTO;
import com.personal.project.service.DTO.EnvelopeDTO;
import com.personal.project.service.DTO.GoalDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/admin")
public interface AdminAPI {
    @GetMapping("/account-old")
    ResponseEntity<List<AccountDTO>> getOldAccounts();

    @GetMapping("/envelopes")
    ResponseEntity<List<EnvelopeDTO>> getAllEnvelopes();

    @GetMapping("/goal")
    ResponseEntity<List<GoalDTO>> getAllGoals();

    @GetMapping("/test-token")
    ResponseEntity<String> testToken(@RequestHeader("Authorization") String token);
}
