package com.personal.project.rest.controller;

import com.personal.project.rest.AccountAPI;
import com.personal.project.service.AccountService;
import com.personal.project.service.DTO.AccountDTO;
import com.personal.project.service.DTO.FullAccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountResource implements AccountAPI {
    private final AccountService accountService;
    @Override
    public ResponseEntity<AccountDTO> getAccount(String token) {
        return ResponseEntity.ok(accountService.getAccountByEmailFromToken(token));
    }

    @Override
    public ResponseEntity<AccountDTO> updateAccount(String token, FullAccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.updateAccountByToken(token, accountDTO));
    }

    @Override
    public ResponseEntity<Void> deleteAccount(String token) {
        accountService.deleteAccountByToken(token);
        return ResponseEntity.noContent().build();
    }
}
