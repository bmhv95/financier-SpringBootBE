package com.personal.project.service.impl;

import com.personal.project.entity.Account;
import com.personal.project.entity.Wallet;
import com.personal.project.repository.WalletRepository;
import com.personal.project.service.AccountService;
import com.personal.project.service.DTO.WalletDTO;
import com.personal.project.service.WalletService;
import com.personal.project.service.mapper.WalletMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final AccountService accountService;

    @Override
    public WalletDTO createNewWallet(String token, WalletDTO walletDTO) {
        Account account = accountService.getAccountEntityFromToken(token);

        Wallet wallet = Wallet.builder()
                .walletName(walletDTO.getWalletName())
                .walletBalance(walletDTO.getWalletBalance())
                .account(account)
                .build();

        return walletMapper.walletToWalletDTO(walletRepository.save(wallet));
    }

    @Override
    public List<WalletDTO> getAllWalletsByToken(String token) {
        return walletMapper.walletListToWalletDTOList(
                walletRepository.getWalletsByAccountID(accountService.getAccountEntityFromToken(token).getAccountID())
        );
    }

    @Override
    public WalletDTO getWalletByID(String token, Long walletID) {
        return walletMapper.walletToWalletDTO(
                getWalletEntityByID(token, walletID)
        );
    }


    @Override
    public Wallet getWalletEntityByID(String token, Long walletID) {
        checkOwnership(token, walletID);
        return walletRepository.findById(walletID).get();
    }

    @Override
    public WalletDTO updateWalletByID(String token, Long walletID, WalletDTO walletDTO) {
        checkOwnership(token, walletID);
        checkDTO(walletDTO);

        Wallet wallet = walletRepository.findById(walletID).get();
        walletMapper.updateWallet(walletDTO, wallet);

        return walletMapper.walletToWalletDTO(
                walletRepository.save(wallet)
        );
    }

    @Override
    public void deleteWalletByID(String token, Long walletID) {
        checkOwnership(token, walletID);
        walletRepository.deleteById(walletID);
    }

    private void checkDTO(WalletDTO walletDTO) {
        return;
    }

    private void checkOwnership(String token, Long walletID) {
        Account account = accountService.getAccountEntityFromToken(token);
        Wallet wallet = walletRepository.findById(walletID).orElseThrow(() -> new RuntimeException("Wallet not found"));
        if (!wallet.getAccount().getAccountID().equals(account.getAccountID())) {
            throw new RuntimeException("You are not the owner of this wallet");
        }
    }
}
