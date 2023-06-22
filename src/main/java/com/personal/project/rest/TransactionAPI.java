package com.personal.project.rest;

import com.personal.project.service.DTO.TransactionDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/transactions")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public interface TransactionAPI {
    @PostMapping("/new")
    ResponseEntity<TransactionDTO> createNewTransaction(@RequestHeader("Authorization") String token, @Valid @RequestBody TransactionDTO transactionDTO);
    @GetMapping
    ResponseEntity<List<TransactionDTO>> getAllTransactions(@RequestHeader("Authorization") String token);

    @GetMapping("/envelopes")
    ResponseEntity<List<TransactionDTO>> getAllTransactionsByEnvelopeID(@RequestHeader("Authorization") String token, @RequestParam("envelopeID") Long envelopeID);

    @GetMapping("/wallets")
    ResponseEntity<List<TransactionDTO>> getAllTransactionsByWalletID(@RequestHeader("Authorization") String token, @RequestParam("walletID") Long walletID);

    @GetMapping("/{id}")
    ResponseEntity<TransactionDTO> getTransactionById(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTransactionById(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @GetMapping("/time")
    ResponseEntity<List<TransactionDTO>> getTransactionsBetweenTime(@RequestHeader("Authorization") String token, @RequestParam("startMonth") String startMonth, @RequestParam("endMonth") String endMonth);
}
