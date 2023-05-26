package com.personal.project.service.impl;

import com.personal.project.entity.EnvelopeTransaction;
import com.personal.project.entity.GoalTransaction;
import com.personal.project.entity.Transaction;
import com.personal.project.repository.*;
import com.personal.project.service.DTO.EnvelopeTransactionDTO;
import com.personal.project.service.DTO.GoalTransactionDTO;
import com.personal.project.service.DTO.TransactionDTO;
import com.personal.project.service.TransactionService;
import com.personal.project.service.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final EnvelopeTransactionRepository envelopeTransactionRepository;
    private final GoalTransactionRepository goalTransactionRepository;
    private final EnvelopeRepository envelopeRepository;
    private final GoalRepository goalRepository;
    private final TransactionMapper transactionMapper;
    private final WalletRepository walletRepository;

    @Override
    public <T extends TransactionDTO> T createTransaction(String token, T transactionDTO) {
        if(transactionDTO instanceof EnvelopeTransactionDTO) {
            return (T) transactionMapper.toEnvelopeDTO(createEnvelopeTransaction((EnvelopeTransactionDTO) transactionDTO));
        } else if (transactionDTO instanceof GoalTransactionDTO) {
            return (T) transactionMapper.toGoalDTO(createGoalTransaction((GoalTransactionDTO) transactionDTO));
        }
        return null; 
    }

    private GoalTransaction createGoalTransaction(GoalTransactionDTO goalTransactionDTO) {
        GoalTransaction goalTransaction = GoalTransaction.builder()
                .transactionAmount(goalTransactionDTO.getTransactionAmount())
                .transactionName(goalTransactionDTO.getTransactionName())
                .transactionComment(goalTransactionDTO.getTransactionComment())
                .goal(goalTransactionDTO.getGoalID() == null ? null
                        : goalRepository.findById(goalTransactionDTO.getGoalID()).get())
                .wallet(goalTransactionDTO.getWalletID() == null ? null
                        : walletRepository.findById(goalTransactionDTO.getWalletID()).get())
                .build();

        return goalTransactionRepository.save(goalTransaction);
    }

    private EnvelopeTransaction createEnvelopeTransaction(EnvelopeTransactionDTO envelopeTransactionDTO) {
        EnvelopeTransaction envelopeTransaction = EnvelopeTransaction.builder()
                .transactionAmount(envelopeTransactionDTO.getTransactionAmount())
                .transactionName(envelopeTransactionDTO.getTransactionName())
                .transactionComment(envelopeTransactionDTO.getTransactionComment())
                .envelope(envelopeTransactionDTO.getEnvelopeID() == null ? null
                        : envelopeRepository.findById(envelopeTransactionDTO.getEnvelopeID()).get())
                .wallet(envelopeTransactionDTO.getWalletID() == null ? null
                        : walletRepository.findById(envelopeTransactionDTO.getWalletID()).get())
                .build();
        return envelopeTransactionRepository.save(envelopeTransaction);
    }
}
