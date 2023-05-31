package com.personal.project.rest;

import com.personal.project.service.DTO.EnvelopeDTO;
import com.personal.project.service.DTO.EnvelopeWithTransactionsDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/envelopes")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public interface EnvelopeAPI {
    @PostMapping({"/new"})
    ResponseEntity<EnvelopeDTO> createNewEnvelope(@RequestHeader("Authorization") String token, @Valid @RequestBody EnvelopeDTO envelopeDTO);

    @GetMapping
    ResponseEntity<List<EnvelopeDTO>> getAllEnvelopesByToken(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    ResponseEntity<EnvelopeDTO> getEnvelopeById(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<EnvelopeDTO> updateEnvelopeById(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody EnvelopeDTO envelopeDTO);

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
}
