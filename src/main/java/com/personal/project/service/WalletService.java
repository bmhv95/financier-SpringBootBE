package com.personal.project.service;

import com.personal.project.entity.Wallet;
import com.personal.project.service.DTO.WalletDTO;

import java.util.List;

public interface WalletService {
    WalletDTO createNewWallet(String token, WalletDTO walletDTO);

    List<WalletDTO> getAllWalletsByToken(String token);

    WalletDTO getWalletByID(String token, Long walletID);

    Wallet getWalletEntityByID(String token, Long walletID);

    WalletDTO updateWalletByID(String token, Long walletID, WalletDTO walletDTO);

    void deleteWalletByID(String token, Long walletID);
}
