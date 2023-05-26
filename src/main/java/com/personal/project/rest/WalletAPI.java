package com.personal.project.rest;

import com.personal.project.service.DTO.WalletDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/wallets")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public interface WalletAPI {
    @PostMapping("/new")
    ResponseEntity<WalletDTO> createNewWallet(@RequestHeader("Authorization") String token, @Valid @RequestBody WalletDTO walletDTO);

    @GetMapping
    ResponseEntity<List<WalletDTO>> getAllWalletsByToken(@RequestHeader("Authorization") String token);

    @GetMapping("/{walletID}")
    ResponseEntity<WalletDTO> getWalletByID(@RequestHeader("Authorization") String token, @PathVariable Long walletID);

    @PutMapping("/{walletID}")
    ResponseEntity<WalletDTO> updateWalletByID(@RequestHeader("Authorization") String token, @PathVariable Long walletID, @Valid @RequestBody WalletDTO walletDTO);

    @DeleteMapping("/{walletID}")
    ResponseEntity<Void> deleteWalletByID(@RequestHeader("Authorization") String token, @PathVariable Long walletID);
}
