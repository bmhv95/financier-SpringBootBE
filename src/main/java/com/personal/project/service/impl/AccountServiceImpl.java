package com.personal.project.service.impl;

import com.personal.project.entity.Account;
import com.personal.project.repository.AccountRepository;
import com.personal.project.service.AccountService;
import com.personal.project.service.DTO.AccountDTO;
import com.personal.project.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountDTO createNewAccount(AccountDTO accountDTO) {
        checkDTO(accountDTO);

        Account newAccount = Account.builder()
                .accountName(accountDTO.getAccountName())
                .email(accountDTO.getEmail())
                .phoneNumber(accountDTO.getPhoneNumber())
                .build();

        return accountMapper.accountToAccountDTO(accountRepository.save(newAccount));
    }
    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountMapper.accountsToAccountDTOs(accountRepository.findAll());
    }

    @Override
    public AccountDTO getAccountByID(Long id) {
        return accountMapper.accountToAccountDTO(accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found")));
    }

    @Override
    public AccountDTO updateAccountByID(Long id, AccountDTO accountDTO) {
        checkDTO(accountDTO);

        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setAccountName(accountDTO.getAccountName());
        account.setEmail(accountDTO.getEmail());
        account.setPhoneNumber(accountDTO.getPhoneNumber());
        return accountMapper.accountToAccountDTO(accountRepository.save(account));
    }

    @Override
    public void deleteAccountByID(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.deleteById(id);
    }

    private void checkDTO(AccountDTO accountDTO) {
        if(accountDTO == null){
            throw new RuntimeException("AccountDTO is null");
        }
    }
}
