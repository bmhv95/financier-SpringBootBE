package com.personal.project.service;

import com.personal.project.entity.Account;
import com.personal.project.entity.AccountRoleAssignment;
import com.personal.project.entity.EnumRole;

import java.util.List;

public interface AccountRoleAssignmentService {
    AccountRoleAssignment createNewAccountRoleAssignment(Account account);

    AccountRoleAssignment addAccountRoleAssignment(Account account, EnumRole role);

    AccountRoleAssignment removeAccountRoleAssignment(Account account, EnumRole role);

    List<AccountRoleAssignment> getRolesOfAccount(Account account);

    List<AccountRoleAssignment> getAccountsOfRole(EnumRole role);
}
