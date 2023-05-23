package com.personal.project.service;

import com.personal.project.entity.Account;
import com.personal.project.entity.AccountRoleAssignment;

public interface AccountRoleAssignmentService {
    public AccountRoleAssignment createNewAccountRoleAssignment(Account account);
}
