package com.personal.project.service.mapper;

import com.personal.project.entity.Account;
import com.personal.project.service.DTO.AccountDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO accountToAccountDTO(Account account);

    List<AccountDTO> accountsToAccountDTOs(List<Account> accounts);
}
