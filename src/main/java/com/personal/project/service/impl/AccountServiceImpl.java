package com.personal.project.service.impl;

import com.personal.project.entity.Account;
import com.personal.project.exception.ExceptionController;
import com.personal.project.repository.AccountRepository;
import com.personal.project.service.AccountRoleAssignmentService;
import com.personal.project.service.AccountService;
import com.personal.project.service.CustomUserDetails;
import com.personal.project.service.DTO.AccountDTO;
import com.personal.project.service.DTO.FullAccountDTO;
import com.personal.project.service.DTO.JwtLoginRequest;
import com.personal.project.service.DTO.JwtResponse;
import com.personal.project.service.mapper.AccountMapper;
import com.personal.project.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    private final AccountRoleAssignmentService accountRoleAssignmentService;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AccountDTO createNewAccount(JwtLoginRequest jwtLoginRequest) {
        if (accountRepository.existsByEmail(jwtLoginRequest.getEmail())) {
            log.warn("Email is already registered: " + jwtLoginRequest.getEmail());
            throw ExceptionController.accountExisted(jwtLoginRequest.getEmail());
        }

        Account account = new Account();
        account.setEmail(jwtLoginRequest.getEmail());
        account.setPassword(passwordEncoder.encode(jwtLoginRequest.getPassword()));
        account.setAccountName(StringUtils.substringBefore(jwtLoginRequest.getEmail(), '@'));
        Account newAccount = accountRepository.save(account);

        accountRoleAssignmentService.createNewAccountRoleAssignment(newAccount);

        return accountMapper.accountToAccountDTO(newAccount);
    }

    @Override
    @Transactional
    public JwtResponse authenticateAccount(JwtLoginRequest jwtLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtLoginRequest.getEmail(), jwtLoginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new JwtResponse(jwt,
                customUserDetails.getEmail(),
                roles);
    }


    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<AccountDTO> getAllAccounts() {
        return accountMapper.accountsToAccountDTOs(accountRepository.findAll());
    }

    @Override
    public AccountDTO getAccountByID(Long id) {
        return accountMapper.accountToAccountDTO(accountRepository.findById(id).orElseThrow(() -> ExceptionController.accountNotFound(String.valueOf(id))));
    }

    @Override
    public AccountDTO getAccountByEmailFromToken(String token) {
        return accountMapper.accountToAccountDTO(getAccountEntityFromToken(token));
    }

    @Override
    public Account getAccountEntityFromToken(String token) {
        String email = jwtUtils.getEmailFromHeader(token);
        return accountRepository.findByEmail(email).orElseThrow(() -> ExceptionController.accountNotFound(email));
    }

    @Override
    public AccountDTO updateAccountByToken(String token, FullAccountDTO accountDTO) {
        String email = jwtUtils.getEmailFromHeader(token);

        return updateAccountByEmail(email, accountDTO);
    }


    @Override
    public AccountDTO updateAccountByID(Long id, FullAccountDTO accountDTO) {
        Account account = accountRepository.findById(id).orElseThrow(() -> ExceptionController.accountNotFound(String.valueOf(id)));
        String email = account.getEmail();
        return updateAccountByEmail(email, accountDTO);
    }

    @Override
    public AccountDTO updateAccountByEmail(String email, FullAccountDTO accountDTO) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> ExceptionController.accountNotFound(email));
        accountMapper.updateAccount(accountDTO, account);
        if(accountDTO.getPassword() != null && !accountDTO.getPassword().isEmpty()){
            account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        }
        return accountMapper.accountToAccountDTO(accountRepository.save(account));
    }

    @Override
    public void deleteAccountByToken(String token) {
        String email = jwtUtils.getEmailFromHeader(token);
        deleteAccountByEmail(email);
    }

    @Override
    public void deleteAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> ExceptionController.accountNotFound(email));
        accountRepository.deleteById(account.getAccountID());
    }

    @Override
    public void deleteAccountByID(Long id) {
        if(!accountRepository.existsById(id)){
            throw ExceptionController.accountNotFound(String.valueOf(id));
        }
        accountRepository.deleteById(id);
    }
}
