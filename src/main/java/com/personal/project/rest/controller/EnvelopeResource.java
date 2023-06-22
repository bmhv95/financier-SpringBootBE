package com.personal.project.rest.controller;

import com.personal.project.rest.EnvelopeAPI;
import com.personal.project.service.DTO.EnvelopeDTO;
import com.personal.project.service.DTO.EnvelopeWithTransactionsDTO;
import com.personal.project.service.DTO.GoalDTO;
import com.personal.project.service.DTO.SpendingDTO;
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
public class EnvelopeResource<T extends EnvelopeDTO> implements EnvelopeAPI {
    private final EnvelopeService<EnvelopeDTO> envelopeService;


    @Override
    public ResponseEntity<SpendingDTO> createNewSpending(String token, SpendingDTO spendingDTO) {
        SpendingDTO newSpending = (SpendingDTO) envelopeService.createNewEnvelope(token, spendingDTO);
        return ResponseEntity.created(URI.create("/api/spending" + newSpending.getID())).body(newSpending);
    }

    @Override
    public ResponseEntity<GoalDTO> createNewGoal(String token, GoalDTO goalDTO) {
        GoalDTO newGoal = (GoalDTO) envelopeService.createNewEnvelope(token, goalDTO);
        return ResponseEntity.created(URI.create("/api/goals/" + newGoal.getID())).body(newGoal);
    }

    @Override
    public ResponseEntity<List<EnvelopeDTO>> getAllEnvelopesByToken(String token) {
        return ResponseEntity.ok(envelopeService.getAllEnvelopesByToken(token));
    }

    @Override
    public ResponseEntity<List<EnvelopeDTO>> getActiveEnvelopesByToken(String token) {
        return ResponseEntity.ok(envelopeService.getActiveEnvelopesByToken(token));
    }

    @Override
    public ResponseEntity<List<GoalDTO>> getAllGoalsByToken(String token) {
        return ResponseEntity.ok(envelopeService.getAllGoals(token));
    }

    @Override
    public ResponseEntity<List<GoalDTO>> getActiveGoalsByToken(String token) {
        return ResponseEntity.ok(envelopeService.getActiveGoals(token));
    }

    @Override
    public ResponseEntity<List<SpendingDTO>> getAllSpendingByToken(String token) {
        return ResponseEntity.ok(envelopeService.getAllSpendings(token));
    }

    @Override
    public ResponseEntity<List<SpendingDTO>> getActiveSpendingByToken(String token) {
        return ResponseEntity.ok(envelopeService.getActiveSpendings(token));
    }

    @Override
    public ResponseEntity<EnvelopeDTO> getEnvelopeById(String token, Long id) {
        return ResponseEntity.ok(envelopeService.getEnvelopeByID(token, id));
    }

    @Override
    public ResponseEntity<EnvelopeDTO> updateSpendingById(String token, Long id, SpendingDTO spendingDTO) {
        return ResponseEntity.ok(envelopeService.updateEnvelopeByID(token, id, spendingDTO));
    }

    @Override
    public ResponseEntity<EnvelopeDTO> updateGoalById(String token, Long id, GoalDTO goalDTO) {
        return ResponseEntity.ok(envelopeService.updateEnvelopeByID(token, id, goalDTO));
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

    @Override
    public ResponseEntity<List<GoalDTO>> getGoalsUnmet(String token) {
        return null;
    }

    @Override
    public ResponseEntity<List<String>> calculateUnmetGoalsAllocations(String token) {
        return null;
    }
}
