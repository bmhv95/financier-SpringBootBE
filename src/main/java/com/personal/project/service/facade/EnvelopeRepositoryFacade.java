package com.personal.project.service.facade;

import com.personal.project.entity.Account;
import com.personal.project.entity.Envelope;
import com.personal.project.entity.Goal;
import com.personal.project.service.DTO.GoalDTO;
import com.personal.project.service.DTO.SpendingDTO;

import java.util.List;

public interface EnvelopeRepositoryFacade<T extends Envelope> {
    List<T> getAllEnvelopesByEmail(String email);
    List<T> getAllEnvelopesByAccountID(Long accountID);
    T getEnvelopeByID(Long envelopeID);
    void deleteEnvelopeByID(Long envelopeID);
}
