package com.personal.project.service;

import com.personal.project.service.DTO.EnvelopeDTO;
import com.personal.project.service.DTO.EnvelopeWithTransactionsDTO;

import java.time.LocalDate;
import java.util.List;

public interface EnvelopeService {

    EnvelopeDTO createNewEnvelope(String token, EnvelopeDTO envelopeDTO);

    List<EnvelopeDTO> getAllEnvelopesByToken(String token);

    EnvelopeDTO getEnvelopeByID(String token, Long envelopeID);

    EnvelopeDTO updateEnvelopeByID(String token, Long envelopeID, EnvelopeDTO envelopeDTO);

    void deleteEnvelopeByID(String token, Long envelopeID);

    List<EnvelopeDTO> getEnvelopesWithNegativeBalance(String token);

    List<EnvelopeDTO> getEnvelopesBelowPercentBalance(String token, Long percentage);

    EnvelopeWithTransactionsDTO getEnvelopeWithTransactions(String token, Long envelopeID);

    List<EnvelopeWithTransactionsDTO> getFullEnvelopesBetweenMonths(String token, LocalDate startDate, LocalDate endDate);

    List<EnvelopeWithTransactionsDTO> getTopNEnvelopesBySpending(String token, int n, LocalDate startDate, LocalDate endDate);
}
