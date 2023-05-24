package com.personal.project.rest;

import com.personal.project.service.DTO.AccountDTO;
import com.personal.project.service.DTO.FullAccountDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/accounts")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public interface AccountAPI {
    @GetMapping
    ResponseEntity<AccountDTO> getAccount(@RequestHeader("Authorization") String token);

    @PutMapping
    ResponseEntity<AccountDTO> updateAccount(@RequestHeader("Authorization") String token, @RequestBody @Valid FullAccountDTO accountDTO);

    @DeleteMapping
    ResponseEntity<Void> deleteAccount(@RequestHeader("Authorization") String token);
}
