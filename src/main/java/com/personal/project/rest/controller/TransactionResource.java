package com.personal.project.rest.controller;

import com.personal.project.rest.TransactionAPI;
import com.personal.project.service.DTO.EnvelopeTransactionDTO;
import com.personal.project.service.DTO.GoalTransactionDTO;
import com.personal.project.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionResource implements TransactionAPI{
    private final TransactionService transactionService;

    @Override
    public ResponseEntity<EnvelopeTransactionDTO> createEnvelopeTransaction(String token, EnvelopeTransactionDTO envelopeTransactionDTO) {
        EnvelopeTransactionDTO newEnvelopeTransaction = transactionService.createTransaction(token, envelopeTransactionDTO);
        return ResponseEntity.created(URI.create("/api/transactions/envelope/" + newEnvelopeTransaction.getTransactionID())).body(newEnvelopeTransaction);
    }

    @Override
    public ResponseEntity<GoalTransactionDTO> createGoalTransaction(String token, GoalTransactionDTO goalTransactionDTO) {
        return null;
    }
}
