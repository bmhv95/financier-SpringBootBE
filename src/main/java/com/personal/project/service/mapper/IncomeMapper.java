package com.personal.project.service.mapper;

import com.personal.project.entity.Income;
import com.personal.project.service.DTO.IncomeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IncomeMapper {
    @Mapping(target = "walletID", source = "wallet.walletID")
    IncomeDTO toIncomeDTO(Income income);

    List<IncomeDTO> toIncomeDTOs(List<Income> incomes);

    @Mapping(target = "incomeID", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    void updateIncome(IncomeDTO incomeDTO, @MappingTarget Income income);
}
