package com.personal.project.rest;

import com.personal.project.entity.Account;
import com.personal.project.service.DTO.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/accounts")
public interface AccountAPI {
    @PostMapping("/new")
    ResponseEntity<AccountDTO> createNewAccount(@RequestBody AccountDTO accountDTO);

    @PostMapping("/login")
    ResponseEntity<AccountDTO> login(@RequestBody AccountDTO accountDTO);

    @GetMapping
    ResponseEntity<List<AccountDTO>> getAllAccounts();

    @GetMapping("/{id}")
    ResponseEntity<AccountDTO> getAccountById(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    ResponseEntity<AccountDTO> updateAccount(@PathVariable("id") Long id, @RequestBody AccountDTO accountDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAccount(@PathVariable("id") Long id);
}
