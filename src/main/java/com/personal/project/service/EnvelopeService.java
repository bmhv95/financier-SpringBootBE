package com.personal.project.service;

import com.personal.project.service.DTO.EnvelopeDTO;
import com.personal.project.service.DTO.EnvelopeWithTransactionsDTO;
import com.personal.project.service.DTO.GoalDTO;
import com.personal.project.service.DTO.SpendingDTO;

import java.time.LocalDate;
import java.util.List;

public interface EnvelopeService<T extends EnvelopeDTO> {

    T createNewEnvelope(String token, T envelopeDTO);

    List<T> getAllEnvelopesByToken(String token);

    List<T> getActiveEnvelopesByToken(String token);

    List<SpendingDTO> getAllSpendings(String token);

    List<SpendingDTO> getActiveSpendings(String token);

    List<GoalDTO> getAllGoals(String token);

    List<GoalDTO> getActiveGoals(String token);

    T getEnvelopeByID(String token, Long envelopeID);

    T updateEnvelopeByID(String token, Long envelopeID, T envelopeDTO);

    void deleteEnvelopeByID(String token, Long envelopeID);

    List<T> getEnvelopesWithNegativeBalance(String token);

    List<T> getEnvelopesBelowPercentBalance(String token, Long percentage);

    EnvelopeWithTransactionsDTO getEnvelopeWithTransactions(String token, Long envelopeID);

    List<EnvelopeWithTransactionsDTO> getFullEnvelopesBetweenMonths(String token, LocalDate startDate, LocalDate endDate);

    List<EnvelopeWithTransactionsDTO> getTopNEnvelopesBySpending(String token, int n, LocalDate startDate, LocalDate endDate);

    List<GoalDTO> getGoalsUnmet(String token);

    List<String> calculateUnmetGoalsAllocations(String token);
}
