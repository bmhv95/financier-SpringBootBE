package com.personal.project.service.mapper;

import com.personal.project.entity.Transaction;
import com.personal.project.service.DTO.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {
    @Mapping(target = "walletID", source = "wallet.ID")
    @Mapping(target = "envelopeID", source = "envelope.ID")
    TransactionDTO toTransactionDTO(Transaction transaction);
    List<TransactionDTO> toTransactionDTOs(List<Transaction> transactions);

    @Mapping(target = "ID", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    @Mapping(target = "envelope", ignore = true)
    void updateTransaction(TransactionDTO transactionDTO, @MappingTarget Transaction transaction);
}
