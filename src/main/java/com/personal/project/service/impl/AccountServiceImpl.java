package com.personal.project.service.impl;

import com.personal.project.entity.Account;
import com.personal.project.entity.AccountRoleAssignment;
import com.personal.project.repository.AccountRepository;
import com.personal.project.service.AccountRoleAssignmentService;
import com.personal.project.service.AccountService;
import com.personal.project.service.CustomUserDetails;
import com.personal.project.service.CustomUserDetailsService;
import com.personal.project.service.DTO.AccountDTO;
import com.personal.project.service.DTO.FullAccountDTO;
import com.personal.project.service.DTO.JwtLoginRequest;
import com.personal.project.service.DTO.JwtResponse;
import com.personal.project.service.mapper.AccountMapper;
import com.personal.project.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    private final AccountRoleAssignmentService accountRoleAssignmentService;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;


//    @Override
//    public AccountDTO createNewAccount(AccountDTO accountDTO) {
//        checkDTO(accountDTO);
//
//        Account newAccount = Account.builder()
//                .accountName(accountDTO.getAccountName())
//                .email(accountDTO.getEmail())
//                .phoneNumber(accountDTO.getPhoneNumber())
//                .build();
//
//        return accountMapper.accountToAccountDTO(accountRepository.save(newAccount));
//    }

    public AccountDTO createNewAccount(JwtLoginRequest jwtLoginRequest) {
        if (accountRepository.existsByEmail(jwtLoginRequest.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        Account account = new Account();
        account.setEmail(jwtLoginRequest.getEmail());
        account.setPassword(passwordEncoder.encode(jwtLoginRequest.getPassword()));
        account.setAccountName(StringUtils.substringBefore(jwtLoginRequest.getEmail(), '@'));
        Account newAccount = accountRepository.save(account);

        AccountRoleAssignment userRole = accountRoleAssignmentService.createNewAccountRoleAssignment(newAccount);

        return accountMapper.accountToAccountDTO(newAccount);
    }

    @Override
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
    public List<AccountDTO> getAllAccounts() {
        return accountMapper.accountsToAccountDTOs(accountRepository.findAll());
    }

    @Override
    public AccountDTO getAccountByID(Long id) {
        return accountMapper.accountToAccountDTO(accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found")));
    }

    @Override
    public AccountDTO getAccountByEmailFromToken(String token) {
        String email = jwtUtils.getEmailFromHeader(token);
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Account not found"));
        return accountMapper.accountToAccountDTO(account);
    }

    @Override
    public AccountDTO updateAccountByToken(String token, FullAccountDTO accountDTO) {
        String email = jwtUtils.getEmailFromHeader(token);

        return updateAccountByEmail(email, accountDTO);
    }


    @Override
    public AccountDTO updateAccountByID(Long id, FullAccountDTO accountDTO) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        String email = account.getEmail();
        return updateAccountByEmail(email, accountDTO);
    }

    @Override
    public AccountDTO updateAccountByEmail(String email, FullAccountDTO accountDTO) {
        checkDTO(accountDTO);

        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setAccountName(accountDTO.getAccountName());
        account.setEmail(accountDTO.getEmail());
        account.setPhoneNumber(accountDTO.getPhoneNumber());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        return accountMapper.accountToAccountDTO(accountRepository.save(account));
    }

    @Override
    public void deleteAccountByToken(String token) {
        String email = jwtUtils.getEmailFromHeader(token);
        deleteAccountByEmail(email);
    }

    @Override
    public void deleteAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.deleteById(account.getAccountID());
    }

    @Override
    public void deleteAccountByID(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.deleteById(id);
    }

    private void checkDTO(FullAccountDTO accountDTO) {
        if(accountDTO == null){
            throw new RuntimeException("AccountDTO is null");
        }
    }
}
