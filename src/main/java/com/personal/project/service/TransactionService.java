package com.personal.project.service;

import com.personal.project.service.DTO.EnvelopeTransactionDTO;
import com.personal.project.service.DTO.GoalTransactionDTO;
import com.personal.project.service.DTO.TransactionDTO;

import java.util.List;

public interface TransactionService {
    <T extends TransactionDTO> T createTransaction(String token, T transactionDTO);

//    List<TransactionDTO> getAllTransactions(String token);
//
//    List<EnvelopeTransactionDTO> getAllEnvelopeTransactions(String token);
//
//    List<GoalTransactionDTO> getAllGoalTransactions(String token);
}
