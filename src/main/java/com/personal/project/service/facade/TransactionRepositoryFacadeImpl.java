package com.personal.project.service.facade;

import com.personal.project.entity.Account;
import com.personal.project.entity.EnvelopeTransaction;
import com.personal.project.entity.GoalTransaction;
import com.personal.project.entity.Transaction;
import com.personal.project.repository.EnvelopeTransactionRepository;
import com.personal.project.repository.GoalTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionRepositoryFacadeImpl implements TransactionRepositoryFacade<Transaction> {
    private final GoalTransactionRepository goalTransactionRepository;
    private final EnvelopeTransactionRepository envelopeTransactionRepository;
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
        } else return envelopeTransaction;
    }

    @Override
    public void deleteTransactionByID(Long transactionID) {
        goalTransactionRepository.deleteById(transactionID);
        envelopeTransactionRepository.deleteById(transactionID);
    }
}
