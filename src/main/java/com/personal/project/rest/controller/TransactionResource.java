package com.personal.project.rest.controller;

import com.personal.project.entity.GoalTransaction;
import com.personal.project.rest.TransactionAPI;
import com.personal.project.service.DTO.EnvelopeTransactionDTO;
import com.personal.project.service.DTO.GoalTransactionDTO;
import com.personal.project.service.DTO.TransactionDTO;
import com.personal.project.service.ReportService;
import com.personal.project.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionResource implements TransactionAPI{
    private final TransactionService<TransactionDTO> transactionService;

    @Override
    public ResponseEntity<EnvelopeTransactionDTO> createEnvelopeTransaction(String token, EnvelopeTransactionDTO envelopeTransactionDTO) {
        EnvelopeTransactionDTO newEnvelopeTransaction = (EnvelopeTransactionDTO) transactionService.createTransaction(token, envelopeTransactionDTO);
        return ResponseEntity.created(URI.create("/api/transactions/envelopes/" + newEnvelopeTransaction.getTransactionID())).body(newEnvelopeTransaction);
    }

    @Override
    public ResponseEntity<GoalTransactionDTO> createGoalTransaction(String token, GoalTransactionDTO goalTransactionDTO) {
        GoalTransactionDTO newGoalTransaction = (GoalTransactionDTO) transactionService.createTransaction(token, goalTransactionDTO);
        return ResponseEntity.created(URI.create("/api/transactions/goals/" + newGoalTransaction.getTransactionID())).body(newGoalTransaction);
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(String token) {
        return ResponseEntity.ok(transactionService.getAllTransactions(token));
    }

    @Override
    public ResponseEntity<List<EnvelopeTransactionDTO>> getAllEnvelopeTransactions(String token) {
        return ResponseEntity.ok(transactionService.getAllEnvelopeTransactions(token));
    }

    @Override
    public ResponseEntity<EnvelopeTransactionDTO> getEnvelopeTransactionByEnvelopeId(String token, Long envelopeID) {
        return ResponseEntity.ok((EnvelopeTransactionDTO) transactionService.getTransactionByID(token, envelopeID));
    }

    @Override
    public ResponseEntity<List<GoalTransactionDTO>> getAllGoalTransactions(String token) {
        return ResponseEntity.ok(transactionService.getAllGoalTransactions(token));
    }

    @Override
    public ResponseEntity<GoalTransactionDTO> getGoalTransactionByGoalId(String token, Long goalID) {
        return ResponseEntity.ok((GoalTransactionDTO) transactionService.getTransactionByID(token, goalID));
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getAllTransactionsByWalletID(String token, Long walletID) {
        return ResponseEntity.ok(transactionService.getAllTransactionsByWalletID(token, walletID));
    }

    @Override
    public ResponseEntity<TransactionDTO> getTransactionById(String token, Long id) {
        return ResponseEntity.ok(transactionService.getTransactionByID(token, id));
    }

    @Override
    public ResponseEntity<EnvelopeTransactionDTO> updateEnvelopeTransactionById(String token, Long id, EnvelopeTransactionDTO envelopeTransactionDTO) {
        return ResponseEntity.ok((EnvelopeTransactionDTO) transactionService.updateTransactionByID(token, id, envelopeTransactionDTO));
    }

    @Override
    public ResponseEntity<GoalTransactionDTO> updateGoalTransactionById(String token, Long id, GoalTransactionDTO goalTransactionDTO) {
        return ResponseEntity.ok((GoalTransactionDTO) transactionService.updateTransactionByID(token, id, goalTransactionDTO));
    }


    @Override
    public ResponseEntity<Void> deleteTransactionById(String token, Long id) {
        transactionService.deleteTransactionByID(token, id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getTransactionsBetweenTime(String token, String startMonth, String endMonth) {
        return ResponseEntity.ok(transactionService.getTransactionsBetweenMonths(token, parseStartMonth(startMonth), parseEndMonth(endMonth)));
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getEnvelopeTransactionsBetweenTime(String token, String startMonth, String endMonth) {
        return ResponseEntity.ok(transactionService.getEnvelopeTransactionsBetweenMonths(token, parseStartMonth(startMonth), parseEndMonth(endMonth)));
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getGoalTransactionsBetweenTime(String token, String startMonth, String endMonth) {
        return ResponseEntity.ok(transactionService.getGoalTransactionsBetweenMonths(token, parseStartMonth(startMonth), parseEndMonth(endMonth)));
    }

    private LocalDate parseStartMonth(String month) {
        YearMonth parseMonth = YearMonth.parse(month);
        return parseMonth.atDay(1);
    }

    private LocalDate parseEndMonth(String month) {
        YearMonth parseMonth = YearMonth.parse(month);
        return parseMonth.atEndOfMonth();
    }
}
