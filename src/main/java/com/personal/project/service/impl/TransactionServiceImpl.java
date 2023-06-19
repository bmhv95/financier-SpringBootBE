package com.personal.project.service.impl;

import com.personal.project.entity.*;
import com.personal.project.exception.ExceptionController;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private final TransactionRepositoryFacade<Transaction> transactionRepositoryFacade;
    private final AccountService accountService;
    private final WalletService walletService;

    @Override
    @Transactional
    public TransactionDTO createTransaction(String token, TransactionDTO transactionDTO) {
        if (transactionDTO instanceof EnvelopeTransactionDTO) {
            return transactionMapper.toEnvelopeDTO(createEnvelopeTransaction(token, (EnvelopeTransactionDTO) transactionDTO));
        } else if (transactionDTO instanceof GoalTransactionDTO) {
            return transactionMapper.toGoalDTO(createGoalTransaction(token, (GoalTransactionDTO) transactionDTO));
        } else {
            log.error("Creating unknown transaction type");
            throw ExceptionController.internalServerError("Unknown transaction type", "TRANSACTION_CREATE_UNKNOWN_TYPE");
        }
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
        checkOwnership(token, transaction);
        if (transaction instanceof EnvelopeTransaction) {
            return transactionMapper.toEnvelopeDTO((EnvelopeTransaction) transaction);
        } else if (transaction instanceof GoalTransaction) {
            return transactionMapper.toGoalDTO((GoalTransaction) transaction);
        }
        return null;
    }

    @Override
    public List<EnvelopeTransactionDTO> getAllEnvelopeTransactions(String token) {
        Account account = accountService.getAccountEntityFromToken(token);
        return transactionMapper.toEnvelopeDTOs(envelopeTransactionRepository.findAllByAccount(account));
    }

    @Override
    public List<GoalTransactionDTO> getAllGoalTransactions(String token) {
        Account account = accountService.getAccountEntityFromToken(token);
        return transactionMapper.toGoalDTOs(goalTransactionRepository.findAllByAccount(account));
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
        return transactionMapper.toGoalDTOs(goalTransactionRepository.findAllByWallet(wallet));
    }

    @Override
    public List<EnvelopeTransactionDTO> getEnvelopeTransactionsByWalletID(String token, Long walletID) {
        Wallet wallet = walletService.getWalletEntityByID(token, walletID);
        return transactionMapper.toEnvelopeDTOs(envelopeTransactionRepository.findAllByWallet(wallet));
    }

    @Override
    @Transactional
    public TransactionDTO updateTransactionByID(String token, Long transactionID, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepositoryFacade.getTransactionByID(transactionID);
        checkOwnership(token, transaction);

        transactionMapper.updateTransaction(transactionDTO, transaction);
        transaction.setWallet(transactionDTO.getWalletID() != null ? walletService.getWalletEntityByID(token, transactionDTO.getWalletID()) : transaction.getWallet());

        if (transaction instanceof EnvelopeTransaction envelopeTransaction) {
            EnvelopeTransactionDTO envelopeTransactionDTO = (EnvelopeTransactionDTO) transactionDTO;
            if (envelopeTransactionDTO.getEnvelopeID() != null) {
                envelopeTransaction.setEnvelope(envelopeRepository.findById(envelopeTransactionDTO.getEnvelopeID()).orElseThrow(() -> ExceptionController.envelopeNotFound(envelopeTransactionDTO.getEnvelopeID())));
            }
            envelopeTransactionRepository.save(envelopeTransaction);
            return transactionMapper.toEnvelopeDTO(envelopeTransaction);
        } else {
            GoalTransaction goalTransaction = (GoalTransaction) transaction;
            GoalTransactionDTO goalTransactionDTO = (GoalTransactionDTO) transactionDTO;
            if (goalTransactionDTO.getGoalID() != null) {
                goalTransaction.setGoal(goalRepository.findById(goalTransactionDTO.getGoalID()).orElseThrow(() -> ExceptionController.goalNotFound(goalTransactionDTO.getGoalID())));
            }
            goalTransactionRepository.save(goalTransaction);
            return transactionMapper.toGoalDTO(goalTransaction);
        }
    }


    @Override
    @Transactional
    public void deleteTransactionByID(String token, Long transactionID) {
        Transaction transaction = transactionRepositoryFacade.getTransactionByID(transactionID);
        checkOwnership(token, transaction);
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

    private GoalTransaction createGoalTransaction(String token, GoalTransactionDTO goalTransactionDTO) {
        GoalTransaction goalTransaction = GoalTransaction.builder()
                .amount(goalTransactionDTO.getAmount())
                .name(goalTransactionDTO.getName())
                .comment(goalTransactionDTO.getComment())
                .goal(goalRepository.findById(goalTransactionDTO.getGoalID()).orElseThrow(() -> ExceptionController.goalNotFound(goalTransactionDTO.getGoalID())))
                .wallet(walletService.getWalletEntityByID(token, goalTransactionDTO.getWalletID()))
                .build();

        return goalTransactionRepository.save(goalTransaction);
    }

    private EnvelopeTransaction createEnvelopeTransaction(String token, EnvelopeTransactionDTO envelopeTransactionDTO) {
        EnvelopeTransaction envelopeTransaction = EnvelopeTransaction.builder()
                .amount(envelopeTransactionDTO.getAmount())
                .name(envelopeTransactionDTO.getName())
                .comment(envelopeTransactionDTO.getComment())
                .envelope(envelopeRepository.findById(envelopeTransactionDTO.getEnvelopeID()).orElseThrow(() -> ExceptionController.envelopeNotFound(envelopeTransactionDTO.getEnvelopeID())))
                .wallet(walletService.getWalletEntityByID(token, envelopeTransactionDTO.getWalletID()))
                .build();
        return envelopeTransactionRepository.save(envelopeTransaction);
    }

    private void checkOwnership(String token, Transaction transaction) {
        Account account = accountService.getAccountEntityFromToken(token);
        if (!transaction.getWallet().getAccount().getID().equals(account.getID())) {
            throw ExceptionController.forbidden();
        }
    }
}
