package com.personal.project.rest.controller;

import com.personal.project.rest.TransactionAPI;
import com.personal.project.service.DTO.EnvelopeTransactionDTO;
import com.personal.project.service.DTO.GoalTransactionDTO;
import com.personal.project.service.DTO.TransactionDTO;
import com.personal.project.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionResource implements TransactionAPI{
    private final TransactionService<TransactionDTO> transactionService;

    @Override
    public ResponseEntity<EnvelopeTransactionDTO> createEnvelopeTransaction(String token, EnvelopeTransactionDTO envelopeTransactionDTO) {
        EnvelopeTransactionDTO newEnvelopeTransaction = (EnvelopeTransactionDTO) transactionService.createTransaction(token, envelopeTransactionDTO);
        return ResponseEntity.created(URI.create("/api/transactions/envelope/" + newEnvelopeTransaction.getTransactionID())).body(newEnvelopeTransaction);
    }

    @Override
    public ResponseEntity<GoalTransactionDTO> createGoalTransaction(String token, GoalTransactionDTO goalTransactionDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(String token) {
        return null;
    }

    @Override
    public ResponseEntity<List<EnvelopeTransactionDTO>> getAllEnvelopeTransactions(String token) {
        return null;
    }

    @Override
    public ResponseEntity<EnvelopeTransactionDTO> getEnvelopeTransactionByEnvelopeId(String token, Long envelopeID) {
        return null;
    }

    @Override
    public ResponseEntity<List<GoalTransactionDTO>> getAllGoalTransactions(String token) {
        return null;
    }

    @Override
    public ResponseEntity<GoalTransactionDTO> getGoalTransactionByGoalId(String token, Long goalID) {
        return null;
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getAllTransactionsByWalletID(String token, Long walletID) {
        return null;
    }

    @Override
    public ResponseEntity<TransactionDTO> getTransactionById(String token, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<TransactionDTO> updateTransactionById(String token, Long id, TransactionDTO transactionDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteTransactionById(String token, Long id) {
        return null;
    }
}
