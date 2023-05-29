package com.personal.project.service.facade;

import com.personal.project.entity.Account;
import com.personal.project.entity.Transaction;
import com.personal.project.service.DTO.TransactionDTO;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepositoryFacade<T extends Transaction> {
    List<T> getAllTransactionsByEmail(String email);
    List<T> getAllTransactionsByAccount(Account account);
    T getTransactionByID(Long transactionID);
    void deleteTransactionByID(Long transactionID);
    List<T> getAllTransactionsBetweenMonths(String email, LocalDate startDate, LocalDate endDate);
}
