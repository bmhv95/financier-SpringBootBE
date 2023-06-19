package com.personal.project.rest.controller;

import com.personal.project.rest.WalletAPI;
import com.personal.project.service.DTO.IncomeDTO;
import com.personal.project.service.DTO.WalletDTO;
import com.personal.project.service.IncomeService;
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
    private final IncomeService incomeService;
    @Override
    public ResponseEntity<WalletDTO> createNewWallet(String token, WalletDTO walletDTO) {
        WalletDTO newWallet = walletService.createNewWallet(token, walletDTO);
        return ResponseEntity.created(URI.create("/api/wallets/" + newWallet.getID())).body(newWallet);
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

    @Override
    public ResponseEntity<List<IncomeDTO>> getIncomesByWalletID(String token, Long walletID) {
        return ResponseEntity.ok(incomeService.getAllIncomesByWalletID(token, walletID));
    }

    @Override
    public ResponseEntity<List<IncomeDTO>> getAllIncomes(String token) {
        return ResponseEntity.ok(incomeService.getAllIncomesByToken(token));
    }

    @Override
    public ResponseEntity<IncomeDTO> createNewIncome(String token, Long walletID, IncomeDTO incomeDTO) {
        IncomeDTO newIncome = incomeService.createNewIncome(token, walletID, incomeDTO);
        return ResponseEntity.created(URI.create("/api/wallets/incomes/" + newIncome.getID())).body(newIncome);
    }

    @Override
    public ResponseEntity<IncomeDTO> updateIncomeByID(String token, Long incomeID, IncomeDTO incomeDTO) {
        return ResponseEntity.ok(incomeService.updateIncomeByID(token, incomeID, incomeDTO));
    }

    @Override
    public ResponseEntity<Void> deleteIncomeByID(String token, Long incomeID) {
        incomeService.deleteIncomeByID(token, incomeID);
        return ResponseEntity.noContent().build();
    }
}
