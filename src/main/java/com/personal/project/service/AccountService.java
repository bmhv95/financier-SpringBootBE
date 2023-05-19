package com.personal.project.service;

import com.personal.project.entity.Account;
import com.personal.project.service.DTO.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createNewAccount(AccountDTO accountDTO);

    List<AccountDTO> getAllAccounts();

    AccountDTO getAccountByID(Long id);

    AccountDTO updateAccountByID(Long id, AccountDTO accountDTO);

    void deleteAccountByID(Long id);
}
