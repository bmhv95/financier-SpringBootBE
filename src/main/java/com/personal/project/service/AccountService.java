package com.personal.project.service;

import com.personal.project.entity.Account;
import com.personal.project.service.DTO.AccountDTO;
import com.personal.project.service.DTO.FullAccountDTO;
import com.personal.project.service.DTO.JwtLoginRequest;
import com.personal.project.service.DTO.JwtResponse;

import java.util.List;

public interface AccountService {
    //creation
    AccountDTO createNewAccount(JwtLoginRequest jwtLoginRequest);

    JwtResponse authenticateAccount(JwtLoginRequest jwtLoginRequest);

    //gets

    List<AccountDTO> getAllAccounts();

    AccountDTO getAccountByID(Long id);

    AccountDTO getAccountByEmailFromToken(String token);

    Account getAccountEntityFromToken(String token);

    //puts

    AccountDTO updateAccountByToken(String token, FullAccountDTO accountDTO);

    AccountDTO updateAccountByID(Long id, FullAccountDTO accountDTO);

    AccountDTO updateAccountByEmail(String email, FullAccountDTO accountDTO);

    //deletes

    void deleteAccountByToken(String token);

    void deleteAccountByEmail(String email);

    void deleteAccountByID(Long id);
}
