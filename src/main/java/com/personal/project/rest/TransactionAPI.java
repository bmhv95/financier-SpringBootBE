package com.personal.project.rest;

import com.personal.project.service.DTO.EnvelopeTransactionDTO;
import com.personal.project.service.DTO.GoalTransactionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/transactions")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public interface TransactionAPI {
    @PostMapping("/envelope")
    ResponseEntity<EnvelopeTransactionDTO> createEnvelopeTransaction(@RequestHeader("Authorization") String token, @RequestBody EnvelopeTransactionDTO envelopeTransactionDTO);

    @PostMapping("/goal")
    ResponseEntity<GoalTransactionDTO> createGoalTransaction(@RequestHeader("Authorization") String token, @RequestBody GoalTransactionDTO goalTransactionDTO);
}
