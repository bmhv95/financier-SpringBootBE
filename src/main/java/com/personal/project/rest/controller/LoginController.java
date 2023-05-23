package com.personal.project.rest.controller;

import com.personal.project.rest.LoginAPI;
import com.personal.project.service.AccountService;
import com.personal.project.service.DTO.AccountDTO;
import com.personal.project.service.DTO.JwtLoginRequest;
import com.personal.project.service.DTO.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class LoginController implements LoginAPI {
    private final AccountService accountService;

    @Override
    public ResponseEntity<JwtResponse> authenticateAccount(JwtLoginRequest jwtLoginRequest) {
        return ResponseEntity.ok(accountService.authenticateAccount(jwtLoginRequest));
    }

    @Override
    public ResponseEntity<AccountDTO> registerAccount(JwtLoginRequest jwtLoginRequest) {
        AccountDTO newAccount = accountService.createNewAccount(jwtLoginRequest);
        return ResponseEntity.created(URI.create("/api/accounts/" + newAccount.getAccountID())).body(newAccount);
    }
}
