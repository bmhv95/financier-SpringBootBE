package com.personal.project.service.impl;

import com.personal.project.entity.Account;
import com.personal.project.entity.Wallet;
import com.personal.project.exception.ExceptionController;
import com.personal.project.repository.WalletRepository;
import com.personal.project.service.AccountService;
import com.personal.project.service.DTO.WalletDTO;
import com.personal.project.service.WalletService;
import com.personal.project.service.mapper.WalletMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
                .walletBalance(walletDTO.getWalletBalance() == null ? BigDecimal.ZERO : walletDTO.getWalletBalance())
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
        Wallet wallet = walletRepository.findById(walletID).orElseThrow(() -> ExceptionController.walletNotFound(walletID));

        checkOwnership(token, wallet);

        return wallet;
    }

    @Override
    public WalletDTO updateWalletByID(String token, Long walletID, WalletDTO walletDTO) {
        Wallet wallet = walletRepository.findById(walletID).orElseThrow(() -> ExceptionController.walletNotFound(walletID));
        checkOwnership(token, wallet);

        walletMapper.updateWallet(walletDTO, wallet);

        return walletMapper.walletToWalletDTO(
                walletRepository.save(wallet)
        );
    }

    @Override
    public void deleteWalletByID(String token, Long walletID) {
        Wallet wallet = walletRepository.findById(walletID).orElseThrow(() -> ExceptionController.walletNotFound(walletID));
        checkOwnership(token, wallet);
        walletRepository.deleteById(walletID);
    }

    private void checkOwnership(String token, Wallet wallet) {
        Account account = accountService.getAccountEntityFromToken(token);
        if (!wallet.getAccount().getAccountID().equals(account.getAccountID())) {
            throw ExceptionController.forbidden();
        }
    }
}
