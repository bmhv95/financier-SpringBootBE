package com.personal.project.rest;

import com.personal.project.service.DTO.EnvelopeDTO;
import com.personal.project.service.DTO.EnvelopeWithTransactionsDTO;
import com.personal.project.service.DTO.GoalDTO;
import com.personal.project.service.DTO.SpendingDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/envelopes")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public interface EnvelopeAPI {
    @PostMapping("/spendings/new")
    ResponseEntity<SpendingDTO> createNewSpending(@RequestHeader("Authorization") String token, @Valid @RequestBody SpendingDTO spendingDTO);

    @PostMapping("/goals/new")
    ResponseEntity<GoalDTO> createNewGoal(@RequestHeader("Authorization") String token, @Valid @RequestBody GoalDTO goalDTO);

    @GetMapping("/all")
    ResponseEntity<List<EnvelopeDTO>> getAllEnvelopesByToken(@RequestHeader("Authorization") String token);

    @GetMapping
    ResponseEntity<List<EnvelopeDTO>> getActiveEnvelopesByToken(@RequestHeader("Authorization") String token);

    @GetMapping("goals/all")
    ResponseEntity<List<GoalDTO>> getAllGoalsByToken(@RequestHeader("Authorization") String token);

    @GetMapping("/goals")
    ResponseEntity<List<GoalDTO>> getActiveGoalsByToken(@RequestHeader("Authorization") String token);

    @GetMapping("spendings/all")
    ResponseEntity<List<SpendingDTO>> getAllSpendingByToken(@RequestHeader("Authorization") String token);

    @GetMapping("spendings")
    ResponseEntity<List<SpendingDTO>> getActiveSpendingByToken(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    ResponseEntity<EnvelopeDTO> getEnvelopeById(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @PutMapping("spending/{id}")
    ResponseEntity<EnvelopeDTO> updateSpendingById(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody SpendingDTO spendingDTO);

    @PutMapping("goal/{id}")
    ResponseEntity<EnvelopeDTO> updateGoalById(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody GoalDTO goalDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEnvelopeById(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @GetMapping("/percentage")
    ResponseEntity<List<EnvelopeDTO>> getEnvelopesBelowPercentBalance(@RequestHeader("Authorization") String token, @RequestParam Long percentage);

    @GetMapping("/negative")
    ResponseEntity<List<EnvelopeDTO>> getEnvelopesWithNegativeBalance(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}/transactions")
    ResponseEntity<EnvelopeWithTransactionsDTO> getEnvelopeWithTransactions(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @GetMapping("/time")
    ResponseEntity<List<EnvelopeWithTransactionsDTO>> getEnvelopesWithTransactionsBetweenMonths(@RequestHeader("Authorization") String token, @RequestParam String startMonth, @RequestParam String endMonth);

    @GetMapping("/sort-by-spending")
    ResponseEntity<List<EnvelopeWithTransactionsDTO>> getTopNEnvelopesBySpending(@RequestHeader("Authorization") String token, @RequestParam int limit, @RequestParam String startMonth, @RequestParam String endMonth);

    @GetMapping("/unmet")
    ResponseEntity<List<GoalDTO>> getGoalsUnmet(@RequestHeader("Authorization") String token);

    @GetMapping("/unmet/calculate")
    ResponseEntity<List<String>> calculateUnmetGoalsAllocations(@RequestHeader("Authorization") String token);
}
