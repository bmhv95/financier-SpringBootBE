package com.personal.project.rest.controller;

import com.personal.project.rest.EnvelopeAPI;
import com.personal.project.service.DTO.EnvelopeDTO;
import com.personal.project.service.DTO.EnvelopeWithTransactionsDTO;
import com.personal.project.service.EnvelopeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EnvelopeResource implements EnvelopeAPI {
    private final EnvelopeService envelopeService;
    @Override
    public ResponseEntity<EnvelopeDTO> createNewEnvelope(String token, EnvelopeDTO envelopeDTO) {
        log.debug("createNewEnvelope");
        EnvelopeDTO newEnvelope = envelopeService.createNewEnvelope(token, envelopeDTO);
        return ResponseEntity.created(URI.create("/api/envelopes/" + newEnvelope.getEnvelopeID())).body(newEnvelope);
    }

    @Override
    public ResponseEntity<List<EnvelopeDTO>> getAllEnvelopesByToken(String token) {
        return ResponseEntity.ok(envelopeService.getAllEnvelopesByToken(token));
    }

    @Override
    public ResponseEntity<EnvelopeDTO> getEnvelopeById(String token, Long id) {
        return ResponseEntity.ok(envelopeService.getEnvelopeByID(token, id));
    }

    @Override
    public ResponseEntity<EnvelopeDTO> updateEnvelopeById(String token, Long id, EnvelopeDTO envelopeDTO) {
        return ResponseEntity.ok(envelopeService.updateEnvelopeByID(token, id, envelopeDTO));
    }

    @Override
    public ResponseEntity<Void> deleteEnvelopeById(String token, Long id) {
        envelopeService.deleteEnvelopeByID(token, id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<EnvelopeDTO>> getEnvelopesBelowPercentBalance(String token, Long percentage) {
        return ResponseEntity.ok(envelopeService.getEnvelopesBelowPercentBalance(token, percentage));
    }

    @Override
    public ResponseEntity<List<EnvelopeDTO>> getEnvelopesWithNegativeBalance(String token) {
        return ResponseEntity.ok(envelopeService.getEnvelopesWithNegativeBalance(token));
    }

    @Override
    public ResponseEntity<EnvelopeWithTransactionsDTO> getEnvelopeWithTransactions(String token, Long id) {
        return ResponseEntity.ok(envelopeService.getEnvelopeWithTransactions(token, id));
    }

    @Override
    public ResponseEntity<List<EnvelopeWithTransactionsDTO>> getEnvelopesWithTransactionsBetweenMonths(String token, String startMonth, String endMonth) {
        LocalDate parseStartMonth = YearMonth.parse(startMonth).atDay(1);
        LocalDate parseEndMonth = YearMonth.parse(endMonth).atEndOfMonth();
        return ResponseEntity.ok(envelopeService.getFullEnvelopesBetweenMonths(token, parseStartMonth, parseEndMonth));
    }

    @Override
    public ResponseEntity<List<EnvelopeWithTransactionsDTO>> getTopNEnvelopesBySpending(String token, int limit, String startMonth, String endMonth) {
        LocalDate parseStartMonth = YearMonth.parse(startMonth).atDay(1);
        LocalDate parseEndMonth = YearMonth.parse(endMonth).atEndOfMonth();
        return ResponseEntity.ok(envelopeService.getTopNEnvelopesBySpending(token, limit, parseStartMonth, parseEndMonth));
    }
}
