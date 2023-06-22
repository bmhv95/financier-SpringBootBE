package com.personal.project.rest.controller;

import com.personal.project.rest.TransactionAPI;
import com.personal.project.service.DTO.TransactionDTO;
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
    private final TransactionService transactionService;
    @Override
    public ResponseEntity<TransactionDTO> createNewTransaction(String token, TransactionDTO transactionDTO) {
        TransactionDTO newTransaction = transactionService.createTransaction(token, transactionDTO);
        return ResponseEntity.created(URI.create("/api/transactions/" + newTransaction.getID())).body(newTransaction);
    }
    @Override
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(String token) {
        return ResponseEntity.ok(transactionService.getAllTransactions(token));
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getAllTransactionsByEnvelopeID(String token, Long envelopeID) {
        return null;
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
    public ResponseEntity<Void> deleteTransactionById(String token, Long id) {
        transactionService.deleteTransactionByID(token, id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getTransactionsBetweenTime(String token, String startMonth, String endMonth) {
        return ResponseEntity.ok(transactionService.getTransactionsBetweenMonths(token, parseStartMonth(startMonth), parseEndMonth(endMonth)));
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
