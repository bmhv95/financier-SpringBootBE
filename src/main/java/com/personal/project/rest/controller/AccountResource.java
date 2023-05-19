package com.personal.project.rest.controller;

import com.personal.project.rest.AccountAPI;
import com.personal.project.service.AccountService;
import com.personal.project.service.DTO.AccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountResource implements AccountAPI {
    private final AccountService accountService;

    @Override
    public ResponseEntity<AccountDTO> createNewAccount(AccountDTO accountDTO) {
        AccountDTO newAccount = accountService.createNewAccount(accountDTO);
        return ResponseEntity.created(URI.create("/api/accounts/" + newAccount.getAccountID())).body(newAccount);
    }

    @Override
    public ResponseEntity<AccountDTO> login(AccountDTO accountDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @Override
    public ResponseEntity<AccountDTO> getAccountById(Long id) {
        return ResponseEntity.ok(accountService.getAccountByID(id));
    }

    @Override
    public ResponseEntity<AccountDTO> updateAccount(Long id, AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.updateAccountByID(id, accountDTO));
    }

    @Override
    public ResponseEntity<Void> deleteAccount(Long id) {
        accountService.deleteAccountByID(id);
        return ResponseEntity.noContent().build();
    }
}
