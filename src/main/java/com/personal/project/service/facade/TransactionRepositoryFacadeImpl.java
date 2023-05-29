package com.personal.project.service.facade;

import com.personal.project.entity.Account;
import com.personal.project.entity.EnvelopeTransaction;
import com.personal.project.entity.GoalTransaction;
import com.personal.project.entity.Transaction;
import com.personal.project.repository.EnvelopeTransactionRepository;
import com.personal.project.repository.GoalTransactionRepository;
import com.personal.project.repository.TransactionRepository;
import com.personal.project.service.DTO.TransactionDTO;
import com.personal.project.service.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TransactionRepositoryFacadeImpl implements TransactionRepositoryFacade<Transaction> {
    private final GoalTransactionRepository goalTransactionRepository;
    private final EnvelopeTransactionRepository envelopeTransactionRepository;
    private final TransactionMapper transactionMapper;
    @Override
    public List<Transaction> getAllTransactionsByEmail(String email) {
        List<Transaction> result = new ArrayList<>();
        result.addAll(goalTransactionRepository.findAllByEmail(email));
        result.addAll(envelopeTransactionRepository.findAllByEmail(email));
        return result;
    }

    @Override
    public List<Transaction> getAllTransactionsByAccount(Account account) {
        List<Transaction> result = new ArrayList<>();
        result.addAll(goalTransactionRepository.findAllByAccount(account));
        result.addAll(envelopeTransactionRepository.findAllByAccount(account));
        return result;
    }


    @Override
    public Transaction getTransactionByID(Long transactionID) {
        GoalTransaction goalTransaction = goalTransactionRepository.findById(transactionID).orElse(null);
        EnvelopeTransaction envelopeTransaction = envelopeTransactionRepository.findById(transactionID).orElse(null);
        if(goalTransaction != null) {
            return goalTransaction;
        } else if(envelopeTransaction != null) {
            return envelopeTransaction;
        } else {
            throw new IllegalArgumentException("Transaction does not exist");
        }
    }

    @Override
    public void deleteTransactionByID(Long transactionID) {
        if(!goalTransactionRepository.existsById(transactionID) && !envelopeTransactionRepository.existsById(transactionID)) {
            throw new IllegalArgumentException("Transaction does not exist");
        }
        goalTransactionRepository.deleteById(transactionID);
        envelopeTransactionRepository.deleteById(transactionID);
    }


    @Override
    public List<Transaction> getAllTransactionsBetweenMonths(String email, LocalDate startDate, LocalDate endDate) {
        List<Transaction> result = new ArrayList<>();
        result.addAll(goalTransactionRepository.findAllBetweenMonths(email, startDate, endDate));
        result.addAll(envelopeTransactionRepository.findAllBetweenMonths(email, startDate, endDate));
        return result;
    }
}
