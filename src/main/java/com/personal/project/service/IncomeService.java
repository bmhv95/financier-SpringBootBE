package com.personal.project.service;

import com.personal.project.service.DTO.IncomeDTO;

import java.util.List;

public interface IncomeService {
    IncomeDTO createNewIncome(String token, Long walletID, IncomeDTO incomeDTO);

    List<IncomeDTO> getAllIncomesByToken(String token);

    List<IncomeDTO> getAllIncomesByWalletID(String token, Long walletID);

    IncomeDTO getIncomeByID(String token, Long incomeID);

    IncomeDTO updateIncomeByID(String token, Long incomeID, IncomeDTO incomeDTO);

    void deleteIncomeByID(String token, Long incomeID);
}
