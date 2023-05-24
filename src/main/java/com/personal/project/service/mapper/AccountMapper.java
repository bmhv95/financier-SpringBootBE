package com.personal.project.service.mapper;

import com.personal.project.entity.Account;
import com.personal.project.service.DTO.AccountDTO;
import com.personal.project.service.DTO.FullAccountDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {
    AccountDTO accountToAccountDTO(Account account);

    List<AccountDTO> accountsToAccountDTOs(List<Account> accounts);

    @Mapping(target = "accountID", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateAccount(FullAccountDTO accountDTO, @MappingTarget Account account);
}
