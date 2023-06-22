package com.personal.project.service.facade;

import com.personal.project.entity.Account;
import com.personal.project.entity.Envelope;
import com.personal.project.entity.Goal;
import com.personal.project.entity.Spending;
import com.personal.project.exception.ExceptionController;
import com.personal.project.repository.EnvelopeRepository;
import com.personal.project.repository.GoalRepository;
import com.personal.project.repository.SpendingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnvelopeRepositoryFacadeImpl implements EnvelopeRepositoryFacade<Envelope> {
    private final SpendingRepository spendingRepository;
    private final GoalRepository goalRepository;

    @Override
    public List<Envelope> getAllEnvelopesByEmail(String email) {
        List<Envelope> result = new ArrayList<>();
        result.addAll(goalRepository.getEnvelopesByEmail(email));
        result.addAll(spendingRepository.getEnvelopesByEmail(email));
        return result;
    }

    @Override
    public List<Envelope> getAllEnvelopesByAccountID(Long accountID) {
        List<Envelope> result = new ArrayList<>();
        result.addAll(goalRepository.getEnvelopesByAccountID(accountID));
        result.addAll(spendingRepository.getEnvelopesByAccountID(accountID));
        return result;
    }

    @Override
    public Envelope getEnvelopeByID(Long envelopeID) {
        return goalRepository.getEnvelopeByID(envelopeID);
    }

    @Override
    public void deleteEnvelopeByID(Long envelopeID) {
        if(!goalRepository.existsById(envelopeID) && !spendingRepository.existsById(envelopeID)) {
            throw ExceptionController.envelopeNotFound(envelopeID);
        }
        goalRepository.deleteById(envelopeID);
        spendingRepository.deleteById(envelopeID);
    }
}
