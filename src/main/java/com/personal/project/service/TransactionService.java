package com.personal.project.service;

import com.personal.project.service.DTO.EnvelopeTransactionDTO;
import com.personal.project.service.DTO.GoalTransactionDTO;
import com.personal.project.service.DTO.TransactionDTO;

import java.util.List;

public interface TransactionService<T extends TransactionDTO> {
    T createTransaction(String token, T transactionDTO);

    List<T> getAllTransactions(String token);

    T getTransactionByID(String token, Long transactionID);

    List<EnvelopeTransactionDTO> getAllEnvelopeTransactions(String token);

    List<GoalTransactionDTO> getAllGoalTransactions(String token);

    List<T> getAllTransactionsByWalletID(String token, Long walletID);

    List<T> getGoalTransactionsByWalletID(String token, Long walletID);

    List<T> getEnvelopeTransactionsByWalletID(String token, Long walletID);

    T updateTransactionByID(String token, Long transactionID, T transactionDTO);

    void deleteTransactionByID(String token, Long transactionID);
}
