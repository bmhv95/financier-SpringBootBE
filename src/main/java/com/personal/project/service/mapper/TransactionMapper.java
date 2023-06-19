package com.personal.project.service.mapper;

import com.personal.project.entity.Transaction;
import com.personal.project.service.DTO.EnvelopeTransactionDTO;
import com.personal.project.service.DTO.GoalTransactionDTO;
import com.personal.project.service.DTO.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {
    @Mapping(source = "goal.ID", target = "goalID")
    @Mapping(source = "wallet.ID", target = "walletID")
    @Mapping(target = "type", expression = "java(getType(goalTransaction))")
    GoalTransactionDTO toGoalDTO(GoalTransaction goalTransaction);

    List<GoalTransactionDTO> toGoalDTOs(List<GoalTransaction> goalTransactions);

    @Mapping(target = "ID", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    void updateTransaction(TransactionDTO transactionDTO, @MappingTarget Transaction transaction);

    @Mapping(source = "envelope.ID", target = "envelopeID")
    @Mapping(source = "wallet.ID", target = "walletID")
    @Mapping(target = "type", expression = "java(getType(envelopeTransaction))")
    EnvelopeTransactionDTO toEnvelopeDTO(EnvelopeTransaction envelopeTransaction);

    List<EnvelopeTransactionDTO> toEnvelopeDTOs(List<EnvelopeTransaction> envelopeTransactions);

    default String getType(Transaction transaction){
        if (transaction instanceof GoalTransaction){
            return "goal";
        }
        if (transaction instanceof EnvelopeTransaction){
            return "envelope";
        }
        return null;
    }

    List<TransactionDTO> toTransactionDTOs(List<Transaction> transactions);

    default <T extends TransactionDTO> T map(Transaction transaction){
        if(transaction instanceof GoalTransaction){
            return (T) toGoalDTO((GoalTransaction) transaction);
        }
        if(transaction instanceof EnvelopeTransaction){
            return (T) toEnvelopeDTO((EnvelopeTransaction) transaction);
        }
        return null;
    }
}
