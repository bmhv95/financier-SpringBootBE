package com.personal.project.service;

import com.personal.project.service.DTO.TransactionDTO;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction(String token, TransactionDTO transactionDTO);

    List<TransactionDTO> getAllTransactions(String token);

    TransactionDTO getTransactionByID(String token, Long transactionID);

    List<TransactionDTO> getAllTransactionsByWalletID(String token, Long walletID);

    List<TransactionDTO> getAllTransactionsByEnvelopeID(String token, Long envelopeID);

    TransactionDTO updateTransactionByID(String token, Long transactionID, TransactionDTO transactionDTO);

    void deleteTransactionByID(String token, Long transactionID);

    List<TransactionDTO> getTransactionsBetweenMonths(String token, LocalDate startDate, LocalDate endDate);
}
