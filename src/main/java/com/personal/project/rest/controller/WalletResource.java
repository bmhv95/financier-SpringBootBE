package com.personal.project.rest.controller;

import com.personal.project.rest.WalletAPI;
import com.personal.project.service.DTO.WalletDTO;
import com.personal.project.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WalletResource implements WalletAPI {
    private final WalletService walletService;
    @Override
    public ResponseEntity<WalletDTO> createNewWallet(String token, WalletDTO walletDTO) {
        WalletDTO newWallet = walletService.createNewWallet(token, walletDTO);
        return ResponseEntity.created(URI.create("/api/wallets/" + newWallet.getWalletID())).body(newWallet);
    }

    @Override
    public ResponseEntity<List<WalletDTO>> getAllWalletsByToken(String token) {
        return ResponseEntity.ok(walletService.getAllWalletsByToken(token));
    }

    @Override
    public ResponseEntity<WalletDTO> getWalletByID(String token, Long walletID) {
        return ResponseEntity.ok(walletService.getWalletByID(token, walletID));
    }

    @Override
    public ResponseEntity<WalletDTO> updateWalletByID(String token, Long walletID, WalletDTO walletDTO) {
        return ResponseEntity.ok(walletService.updateWalletByID(token, walletID, walletDTO));
    }

    @Override
    public ResponseEntity<Void> deleteWalletByID(String token, Long walletID) {
        walletService.deleteWalletByID(token, walletID);
        return ResponseEntity.noContent().build();
    }
}
