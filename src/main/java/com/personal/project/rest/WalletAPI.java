package com.personal.project.rest;

import com.personal.project.service.DTO.WalletDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/wallets")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public interface WalletAPI {
    @PostMapping("/new")
    ResponseEntity<WalletDTO> createNewWallet(@RequestHeader("Authorization") String token, @Valid @RequestBody WalletDTO walletDTO);
}
