package com.personal.project.service.impl;

import com.personal.project.entity.*;
import com.personal.project.repository.*;
import com.personal.project.service.AccountService;
import com.personal.project.service.DTO.EnvelopeTransactionDTO;
import com.personal.project.service.DTO.GoalTransactionDTO;
import com.personal.project.service.DTO.TransactionDTO;
import com.personal.project.service.TransactionService;
import com.personal.project.service.WalletService;
import com.personal.project.service.facade.TransactionRepositoryFacade;
import com.personal.project.service.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl<T extends TransactionDTO> implements TransactionService {
    private final EnvelopeTransactionRepository envelopeTransactionRepository;
    private final GoalTransactionRepository goalTransactionRepository;
    private final EnvelopeRepository envelopeRepository;
    private final GoalRepository goalRepository;
    private final TransactionMapper transactionMapper;
    private final WalletRepository walletRepository;
    private final TransactionRepositoryFacade<Transaction> transactionRepositoryFacade;
    private final AccountService accountService;
    private final WalletService walletService;

    @Override
    public TransactionDTO createTransaction(String token, TransactionDTO transactionDTO) {
        if(transactionDTO instanceof EnvelopeTransactionDTO) {
            return transactionMapper.toEnvelopeDTO(createEnvelopeTransaction((EnvelopeTransactionDTO) transactionDTO));
        } else if (transactionDTO instanceof GoalTransactionDTO) {
            return transactionMapper.toGoalDTO(createGoalTransaction((GoalTransactionDTO) transactionDTO));
        }
        return null; 
    }

    @Override
    public List<TransactionDTO> getAllTransactions(String token) {
        List<TransactionDTO> result = new ArrayList<>();
        result.addAll(getAllEnvelopeTransactions(token));
        result.addAll(getAllGoalTransactions(token));
        return result;
    }

    public TransactionDTO getTransactionByID(String token, Long transactionID) {
        Transaction transaction = transactionRepositoryFacade.getTransactionByID(transactionID);
        if(transaction instanceof EnvelopeTransaction) {
            return transactionMapper.toEnvelopeDTO((EnvelopeTransaction) transaction);
        } else if (transaction instanceof GoalTransaction) {
            return transactionMapper.toGoalDTO((GoalTransaction) transaction);
        }
        return null;
    }

    @Override
    public List<EnvelopeTransactionDTO> getAllEnvelopeTransactions(String token) {
        Account account = accountService.getAccountEntityFromToken(token);
        return transactionMapper.toEnvelopeDTOs((List<EnvelopeTransaction>) envelopeTransactionRepository.findAllByAccount(account));
    }

    @Override
    public List<GoalTransactionDTO> getAllGoalTransactions(String token) {
        Account account = accountService.getAccountEntityFromToken(token);
        return transactionMapper.toGoalDTOs((List<GoalTransaction>) goalTransactionRepository.findAllByAccount(account));
    }

    @Override
    public List<TransactionDTO> getAllTransactionsByWalletID(String token, Long walletID) {
        List<TransactionDTO> result = new ArrayList<>();
        result.addAll(getEnvelopeTransactionsByWalletID(token, walletID));
        result.addAll(getGoalTransactionsByWalletID(token, walletID));
        return result;
    }

    @Override
    public List<GoalTransactionDTO> getGoalTransactionsByWalletID(String token, Long walletID) {
        Wallet wallet = walletService.getWalletEntityByID(token, walletID);
        return transactionMapper.toGoalDTOs((List<GoalTransaction>) goalTransactionRepository.findAllByWallet(wallet));
    }

    @Override
    public List<EnvelopeTransactionDTO> getEnvelopeTransactionsByWalletID(String token, Long walletID) {
        Wallet wallet = walletService.getWalletEntityByID(token, walletID);
        return transactionMapper.toEnvelopeDTOs((List<EnvelopeTransaction>) envelopeTransactionRepository.findAllByWallet(wallet));
    }

    @Override
    public TransactionDTO updateTransactionByID(String token, Long transactionID, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepositoryFacade.getTransactionByID(transactionID);
        if(transaction == null) {
            throw new RuntimeException("Transaction not found");
        }

        transactionMapper.updateTransaction(transactionDTO, transaction);
        transaction.setWallet(walletRepository.findById(transactionDTO.getWalletID()).orElseThrow(() -> new RuntimeException("Wallet not found")));

        if(transaction instanceof EnvelopeTransaction envelopeTransaction) {
            EnvelopeTransactionDTO envelopeTransactionDTO = (EnvelopeTransactionDTO) transactionDTO;
            envelopeTransaction.setEnvelope(envelopeRepository.findById(envelopeTransactionDTO.getEnvelopeID()).orElseThrow(() -> new RuntimeException("Envelope not found")));
            envelopeTransactionRepository.save(envelopeTransaction);
            return transactionMapper.toEnvelopeDTO(envelopeTransaction);
        } else {
            GoalTransaction goalTransaction = (GoalTransaction) transaction;
            GoalTransactionDTO goalTransactionDTO = (GoalTransactionDTO) transactionDTO;
            goalTransaction.setGoal(goalRepository.findById(goalTransactionDTO.getGoalID()).orElseThrow(() -> new RuntimeException("Goal not found")));
            goalTransactionRepository.save(goalTransaction);
            return transactionMapper.toGoalDTO(goalTransaction);
        }
    }


    @Override
    public void deleteTransactionByID(String token, Long transactionID) {
        checkOwnership(token, transactionID);
        Transaction transaction = transactionRepositoryFacade.getTransactionByID(transactionID);
        if(transaction == null) {
            throw new RuntimeException("Transaction not found");
        }
        transactionRepositoryFacade.deleteTransactionByID(transactionID);
    }

    @Override
    public List<TransactionDTO> getTransactionsBetweenMonths(String token, LocalDate startDate, LocalDate endDate) {
        String email = accountService.getAccountEntityFromToken(token).getEmail();
        return transactionMapper.toTransactionDTOs(transactionRepositoryFacade.getAllTransactionsBetweenMonths(email, startDate, endDate));
    }

    @Override
    public List<EnvelopeTransactionDTO> getEnvelopeTransactionsBetweenMonths(String token, LocalDate startDate, LocalDate endDate) {
        String email = accountService.getAccountEntityFromToken(token).getEmail();
        return transactionMapper.toEnvelopeDTOs(envelopeTransactionRepository.findAllBetweenMonths(email, startDate, endDate));
    }

    @Override
    public List<GoalTransactionDTO> getGoalTransactionsBetweenMonths(String token, LocalDate startDate, LocalDate endDate) {
        String email = accountService.getAccountEntityFromToken(token).getEmail();
        return transactionMapper.toGoalDTOs(goalTransactionRepository.findAllBetweenMonths(email, startDate, endDate));
    }

    private GoalTransaction createGoalTransaction(GoalTransactionDTO goalTransactionDTO) {
        GoalTransaction goalTransaction = GoalTransaction.builder()
                .transactionAmount(goalTransactionDTO.getTransactionAmount())
                .transactionName(goalTransactionDTO.getTransactionName())
                .transactionComment(goalTransactionDTO.getTransactionComment())
                .goal(goalRepository.findById(goalTransactionDTO.getGoalID()).orElseThrow(() -> new RuntimeException("Goal not found")))
                .wallet(walletRepository.findById(goalTransactionDTO.getWalletID()).orElseThrow(() -> new RuntimeException("Wallet not found")))
                .build();

        return goalTransactionRepository.save(goalTransaction);
    }

    private EnvelopeTransaction createEnvelopeTransaction(EnvelopeTransactionDTO envelopeTransactionDTO) {
        EnvelopeTransaction envelopeTransaction = EnvelopeTransaction.builder()
                .transactionAmount(envelopeTransactionDTO.getTransactionAmount())
                .transactionName(envelopeTransactionDTO.getTransactionName())
                .transactionComment(envelopeTransactionDTO.getTransactionComment())
                .envelope(envelopeRepository.findById(envelopeTransactionDTO.getEnvelopeID()).orElseThrow(() -> new RuntimeException("Envelope not found")))
                .wallet(walletRepository.findById(envelopeTransactionDTO.getWalletID()).orElseThrow(() -> new RuntimeException("Wallet not found")))
                .build();
        return envelopeTransactionRepository.save(envelopeTransaction);
    }

    private void checkOwnership(String token, Long transactionID) {
        Account account = accountService.getAccountEntityFromToken(token);
        Transaction transaction = transactionRepositoryFacade.getTransactionByID(transactionID);
        if(!transaction.getWallet().getAccount().equals(account)) {
            throw new RuntimeException("You are not the owner of this transaction");
        }
    }
}
