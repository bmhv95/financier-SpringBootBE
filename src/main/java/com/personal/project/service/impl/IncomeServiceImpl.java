package com.personal.project.service.impl;

import com.personal.project.entity.Account;
import com.personal.project.entity.Income;
import com.personal.project.entity.Wallet;
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
    public IncomeDTO createNewIncome(String token, IncomeDTO incomeDTO) {
        checkDTO(incomeDTO);

        Account account = accountService.getAccountEntityFromToken(token);

        Income income = Income.builder()
                .incomeName(incomeDTO.getIncomeName())
                .incomeAmount(incomeDTO.getIncomeAmount())
                .wallet(walletService.getWalletEntityByID(token, incomeDTO.getWalletID()))
                .build();

        return incomeMapper.toIncomeDTO(incomeRepository.save(income));
    }

    @Override
    public List<IncomeDTO> getAllIncomesByToken(String token) {
        return null;
    }

    @Override
    public List<IncomeDTO> getAllIncomesByWalletID(String token, Long walletID) {
        return null;
    }

    @Override
    public IncomeDTO getIncomeByID(String token, Long incomeID) {
        return null;
    }

    @Override
    public IncomeDTO updateIncomeByID(String token, Long incomeID, IncomeDTO incomeDTO) {
        return null;
    }

    @Override
    public void deleteIncomeByID(String token, Long incomeID) {

    }

    private void checkDTO(IncomeDTO incomeDTO) {
        return;
    }

    private void checkOwnership(String token, Long incomeID) {
        Account account = accountService.getAccountEntityFromToken(token);
        Income income = incomeRepository.findById(incomeID).orElseThrow(() -> new RuntimeException("Income not found"));
        if (!income.getWallet().getAccount().getAccountID().equals(account.getAccountID())) {
            throw new RuntimeException("You are not the owner of this income");
        }
    }
}
