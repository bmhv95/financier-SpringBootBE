package com.personal.project.service.impl;

import com.personal.project.entity.*;
import com.personal.project.exception.ExceptionController;
import com.personal.project.repository.*;
import com.personal.project.service.AccountService;
import com.personal.project.service.DTO.TransactionDTO;
import com.personal.project.service.TransactionService;
import com.personal.project.service.WalletService;
import com.personal.project.service.facade.EnvelopeRepositoryFacade;
import com.personal.project.service.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final GoalRepository goalRepository;
    private final TransactionMapper transactionMapper;
    private final AccountService accountService;
    private final WalletService walletService;
    private final EnvelopeRepositoryFacade<Envelope> envelopeRepositoryFacade;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public TransactionDTO createTransaction(String token, TransactionDTO transactionDTO) {
       Account account = accountService.getAccountEntityFromToken(token);

       Transaction transaction = Transaction.builder()
               .name(transactionDTO.getName())
               .amount(transactionDTO.getAmount())
               .comment(transactionDTO.getComment())
               .date(LocalDate.now())
               .wallet(walletService.getWalletEntityByID(token, transactionDTO.getWalletID()))
               .envelope(envelopeRepositoryFacade.getEnvelopeByID(transactionDTO.getEnvelopeID()))
               .build();

       return transactionMapper.toTransactionDTO(transactionRepository.saveAndFlush(transaction));
    }

    @Override
    public List<TransactionDTO> getAllTransactions(String token) {
        return transactionMapper.toTransactionDTOs(transactionRepository.findAllByAccountID(accountService.getAccountEntityFromToken(token).getID()));
    }

    public TransactionDTO getTransactionByID(String token, Long transactionID) {
        Transaction transaction = transactionRepository.findById(transactionID).orElseThrow(() -> ExceptionController.transactionNotFound(transactionID));
        checkOwnership(token, transaction);
        return transactionMapper.toTransactionDTO(transaction);
    }

    @Override
    public List<TransactionDTO> getAllTransactionsByWalletID(String token, Long walletID) {
        return transactionMapper.toTransactionDTOs(transactionRepository.findAllByWallet(walletService.getWalletEntityByID(token, walletID)));
    }

    @Override
    public List<TransactionDTO> getAllTransactionsByEnvelopeID(String token, Long envelopeID) {
        return transactionMapper.toTransactionDTOs(transactionRepository.findAllByEnvelope(envelopeRepositoryFacade.getEnvelopeByID(envelopeID)));
    }

    @Override
    @Transactional
    public TransactionDTO updateTransactionByID(String token, Long transactionID, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(transactionID).orElseThrow(() -> ExceptionController.transactionNotFound(transactionID));
        checkOwnership(token, transaction);

        transactionMapper.updateTransaction(transactionDTO, transaction);
        transaction.setWallet(transactionDTO.getWalletID() != null ? walletService.getWalletEntityByID(token, transactionDTO.getWalletID()) : transaction.getWallet());
        transaction.setEnvelope(transactionDTO.getEnvelopeID() != null ? envelopeRepositoryFacade.getEnvelopeByID(transactionDTO.getEnvelopeID()) : transaction.getEnvelope());
        return transactionMapper.toTransactionDTO(transactionRepository.saveAndFlush(transaction));
    }


    @Override
    @Transactional
    public void deleteTransactionByID(String token, Long transactionID) {
        Transaction transaction = transactionRepository.findById(transactionID).orElseThrow(() -> ExceptionController.transactionNotFound(transactionID));
        checkOwnership(token, transaction);
        transactionRepository.deleteById(transactionID);
    }

    @Override
    public List<TransactionDTO> getTransactionsBetweenMonths(String token, LocalDate startDate, LocalDate endDate) {
        String email = accountService.getAccountEntityFromToken(token).getEmail();
        return transactionMapper.toTransactionDTOs(transactionRepository.getAllTransactionsBetweenMonths(email, startDate, endDate));
    }

    private void checkOwnership(String token, Transaction transaction) {
        Account account = accountService.getAccountEntityFromToken(token);
        if (!transaction.getWallet().getAccount().getID().equals(account.getID())) {
            throw ExceptionController.forbidden();
        }
    }
}
