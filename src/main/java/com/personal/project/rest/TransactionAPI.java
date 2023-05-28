package com.personal.project.rest;

import com.personal.project.service.DTO.EnvelopeTransactionDTO;
import com.personal.project.service.DTO.GoalTransactionDTO;
import com.personal.project.service.DTO.TransactionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/transactions")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public interface TransactionAPI {
    @PostMapping("/envelope/new")
    ResponseEntity<EnvelopeTransactionDTO> createEnvelopeTransaction(@RequestHeader("Authorization") String token, @RequestBody EnvelopeTransactionDTO envelopeTransactionDTO);

    @PostMapping("/goal/new")
    ResponseEntity<GoalTransactionDTO> createGoalTransaction(@RequestHeader("Authorization") String token, @RequestBody GoalTransactionDTO goalTransactionDTO);

    @GetMapping
    ResponseEntity<List<TransactionDTO>> getAllTransactions(@RequestHeader("Authorization") String token);

    @GetMapping("/envelope")
    ResponseEntity<List<EnvelopeTransactionDTO>> getAllEnvelopeTransactions(@RequestHeader("Authorization") String token);

    @GetMapping("/envelope/{envelopeID}")
    ResponseEntity<EnvelopeTransactionDTO> getEnvelopeTransactionByEnvelopeId(@RequestHeader("Authorization") String token, @PathVariable Long envelopeID);

    @GetMapping("/goal")
    ResponseEntity<List<GoalTransactionDTO>> getAllGoalTransactions(@RequestHeader("Authorization") String token);

    @GetMapping("/goal/{goalID}")
    ResponseEntity<GoalTransactionDTO> getGoalTransactionByGoalId(@RequestHeader("Authorization") String token, @PathVariable Long goalID);

    @GetMapping("/wallet")
    ResponseEntity<List<TransactionDTO>> getAllTransactionsByWalletID(@RequestHeader("Authorization") String token, @RequestParam("walletID") Long walletID);

    @GetMapping("/{id}")
    ResponseEntity<TransactionDTO> getTransactionById(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<TransactionDTO> updateTransactionById(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody TransactionDTO transactionDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTransactionById(@RequestHeader("Authorization") String token, @PathVariable Long id);
}
