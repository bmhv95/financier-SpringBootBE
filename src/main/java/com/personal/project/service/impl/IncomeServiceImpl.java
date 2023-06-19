package com.personal.project.service.impl;

import com.personal.project.entity.Account;
import com.personal.project.entity.Income;
import com.personal.project.entity.Wallet;
import com.personal.project.exception.ExceptionController;
import com.personal.project.repository.IncomeRepository;
import com.personal.project.service.AccountService;
import com.personal.project.service.DTO.IncomeDTO;
import com.personal.project.service.DTO.WalletDTO;
import com.personal.project.service.IncomeService;
import com.personal.project.service.WalletService;
import com.personal.project.service.mapper.IncomeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncomeServiceImpl implements IncomeService {
    private final IncomeMapper incomeMapper;
    private final IncomeRepository incomeRepository;
    private final AccountService accountService;
    private final WalletService walletService;
    @Override
    public IncomeDTO createNewIncome(String token, Long walletID, IncomeDTO incomeDTO) {
        Account account = accountService.getAccountEntityFromToken(token);

        Income income = Income.builder()
                .name(incomeDTO.getName())
                .amount(incomeDTO.getAmount())
                .wallet(walletService.getWalletEntityByID(token, walletID))
                .build();

        return incomeMapper.toIncomeDTO(incomeRepository.save(income));
    }

    @Override
    public List<IncomeDTO> getAllIncomesByToken(String token) {
        Long accountID = accountService.getAccountEntityFromToken(token).getID();
        return incomeMapper.toIncomeDTOs(incomeRepository.getIncomesByAccountID(accountID));
    }

    @Override
    public List<IncomeDTO> getAllIncomesByWalletID(String token, Long walletID) {
        Wallet wallet = walletService.getWalletEntityByID(token, walletID);
        List<Income> incomes = incomeRepository.findByWallet(wallet);
        return incomeMapper.toIncomeDTOs(incomes);
    }

    @Override
    public IncomeDTO getIncomeByID(String token, Long incomeID) {
        Income income = incomeRepository.findById(incomeID).orElseThrow(() -> ExceptionController.incomeNotFound(incomeID));
        checkOwnership(token, income);
        return incomeMapper.toIncomeDTO(income);
    }

    @Override
    public IncomeDTO updateIncomeByID(String token, Long incomeID, IncomeDTO incomeDTO) {
        Income income = incomeRepository.findById(incomeID).orElseThrow(() -> ExceptionController.incomeNotFound(incomeID));
        checkOwnership(token, income);
        incomeMapper.updateIncome(incomeDTO, income);

        Wallet wallet = walletService.getWalletEntityByID(token, incomeDTO.getWalletID());
        income.setWallet(wallet);
        return incomeMapper.toIncomeDTO(income);
    }

    @Override
    public void deleteIncomeByID(String token, Long incomeID) {
        Income income = incomeRepository.findById(incomeID).orElseThrow(() -> ExceptionController.incomeNotFound(incomeID));
        checkOwnership(token, income);
        incomeRepository.deleteById(incomeID);
    }

    private void checkOwnership(String token, Income income) {
        Account account = accountService.getAccountEntityFromToken(token);
        if (!income.getWallet().getAccount().getID().equals(account.getID())) {
            throw new RuntimeException("You are not the owner of this income");
        }
    }
}
