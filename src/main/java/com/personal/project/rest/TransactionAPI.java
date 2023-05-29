package com.personal.project.rest;

import com.personal.project.service.DTO.EnvelopeTransactionDTO;
import com.personal.project.service.DTO.GoalTransactionDTO;
import com.personal.project.service.DTO.TransactionDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/transactions")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public interface TransactionAPI {
    @PostMapping("/envelopes/new")
    ResponseEntity<EnvelopeTransactionDTO> createEnvelopeTransaction(@RequestHeader("Authorization") String token, @Valid @RequestBody EnvelopeTransactionDTO envelopeTransactionDTO);

    @PostMapping("/goals/new")
    ResponseEntity<GoalTransactionDTO> createGoalTransaction(@RequestHeader("Authorization") String token, @Valid @RequestBody GoalTransactionDTO goalTransactionDTO);

    @GetMapping
    ResponseEntity<List<TransactionDTO>> getAllTransactions(@RequestHeader("Authorization") String token);

    @GetMapping("/envelopes")
    ResponseEntity<List<EnvelopeTransactionDTO>> getAllEnvelopeTransactions(@RequestHeader("Authorization") String token);

    @GetMapping("/envelopes/{envelopeID}")
    ResponseEntity<EnvelopeTransactionDTO> getEnvelopeTransactionByEnvelopeId(@RequestHeader("Authorization") String token, @PathVariable Long envelopeID);

    @GetMapping("/goals")
    ResponseEntity<List<GoalTransactionDTO>> getAllGoalTransactions(@RequestHeader("Authorization") String token);

    @GetMapping("/goals/{goalID}")
    ResponseEntity<GoalTransactionDTO> getGoalTransactionByGoalId(@RequestHeader("Authorization") String token, @PathVariable Long goalID);

    @GetMapping("/wallets")
    ResponseEntity<List<TransactionDTO>> getAllTransactionsByWalletID(@RequestHeader("Authorization") String token, @RequestParam("walletID") Long walletID);

    @GetMapping("/{id}")
    ResponseEntity<TransactionDTO> getTransactionById(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @PutMapping("/envelopes/{id}")
    ResponseEntity<EnvelopeTransactionDTO> updateEnvelopeTransactionById(@RequestHeader("Authorization") String token, @PathVariable Long id, @Valid @RequestBody EnvelopeTransactionDTO envelopeTransactionDTO);

    @PutMapping("/goals/{id}")
    ResponseEntity<GoalTransactionDTO> updateGoalTransactionById(@RequestHeader("Authorization") String token, @PathVariable Long id, @Valid @RequestBody GoalTransactionDTO goalTransactionDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTransactionById(@RequestHeader("Authorization") String token, @PathVariable Long id);
}
