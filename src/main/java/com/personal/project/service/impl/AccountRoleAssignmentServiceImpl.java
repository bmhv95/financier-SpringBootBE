package com.personal.project.service.impl;

import com.personal.project.entity.Account;
import com.personal.project.entity.AccountRoleAssignment;
import com.personal.project.entity.EnumRole;
import com.personal.project.repository.AccountRoleAssignmentRepository;
import com.personal.project.service.AccountRoleAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRoleAssignmentServiceImpl implements AccountRoleAssignmentService {
    private final AccountRoleAssignmentRepository accountRoleAssignmentRepository;
    @Override
    public AccountRoleAssignment createNewAccountRoleAssignment(Account account) {
        return accountRoleAssignmentRepository.save(AccountRoleAssignment.builder()
                .account(account)
                .role(EnumRole.ROLE_USER)
                .build());
    }
}
