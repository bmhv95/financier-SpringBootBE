package com.personal.project.service;

import com.personal.project.service.DTO.EnvelopeDTO;
import com.personal.project.service.DTO.EnvelopeWithTransactionsDTO;
import com.personal.project.service.DTO.GoalDTO;
import com.personal.project.service.DTO.TransactionDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
//    List<TransactionDTO> getTransactionsBetweenMonths(String token, LocalDate startDate, LocalDate endDate);

//    List<EnvelopeDTO> getEnvelopesBetweenMonths(String token, LocalDate startDate, LocalDate endDate);

//    List<EnvelopeDTO> getEnvelopesWithNegativeBalance(String token);

//    List<EnvelopeWithTransactionsDTO> getFullEnvelopesBetweenMonths(String token, LocalDate startDate, LocalDate endDate);

//    List<EnvelopeDTO> getEnvelopesBelowPercentBalance(String token, Long percentage);

    List<GoalDTO> getGoalsUnmet(String token);

    List<EnvelopeDTO> getTopNEnvelopesByTransactions(String token, int n);

    String monthlyTransactionReport(String token, LocalDate date);
}
