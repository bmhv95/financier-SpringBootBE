package com.personal.project.service.impl;

import com.personal.project.entity.Account;
import com.personal.project.entity.Envelope;
import com.personal.project.entity.Goal;
import com.personal.project.entity.Spending;
import com.personal.project.exception.ExceptionController;
import com.personal.project.repository.EnvelopeRepository;
import com.personal.project.repository.GoalRepository;
import com.personal.project.repository.SpendingRepository;
import com.personal.project.service.AccountService;
import com.personal.project.service.DTO.*;
import com.personal.project.service.EnvelopeService;
import com.personal.project.service.facade.EnvelopeRepositoryFacade;
import com.personal.project.service.mapper.EnvelopeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnvelopeServiceImpl implements EnvelopeService {
    private final SpendingRepository spendingRepository;
    private final GoalRepository goalRepository;
    private final EnvelopeMapper envelopeMapper;
    private final AccountService accountService;
    private final EnvelopeRepositoryFacade<Envelope> envelopeRepositoryFacade;
    @Override
    public EnvelopeDTO createNewEnvelope(String token, EnvelopeDTO envelopeDTO) {
        if(envelopeDTO instanceof SpendingDTO){
            return envelopeMapper.toSpendingDTO(createSpending(token, (SpendingDTO) envelopeDTO));
        } else if (envelopeDTO instanceof GoalDTO){
            return envelopeMapper.toGoalDTO(createGoal(token, (GoalDTO) envelopeDTO));
        } else {
            log.error("Creating unknown envelope type");
            throw ExceptionController.internalServerError("Unknown envelope type", "ENVELOPE_CREATE_UNKNOWN_TYPE");
        }
    }

    private Spending createSpending(String token, SpendingDTO spendingDTO){
        Spending spending = Spending.builder()
                .name(spendingDTO.getName())
                .description(spendingDTO.getDescription())
                .budget(spendingDTO.getBudget())
                .spent(BigDecimal.ZERO)
                .allocated(BigDecimal.ZERO)
                .account(accountService.getAccountEntityFromToken(token))
                .isActive(true)
                .date(spendingDTO.getDate() == null ? LocalDate.now() : spendingDTO.getDate())
                .periodType(spendingDTO.getPeriodType())
                .periodCount(spendingDTO.getPeriodCount())
                .build();

        return spendingRepository.save(spending);
    }

    private Goal createGoal(String token, GoalDTO goalDTO){
        Goal goal = Goal.builder()
                .name(goalDTO.getName())
                .description(goalDTO.getDescription())
                .budget(goalDTO.getBudget())
                .spent(BigDecimal.ZERO)
                .allocated(BigDecimal.ZERO)
                .account(accountService.getAccountEntityFromToken(token))
                .isActive(true)
                .endDate(goalDTO.getEndDate())
                .build();

        return goalRepository.save(goal);
    }

    @Override
    public List<EnvelopeDTO> getAllEnvelopesByToken(String token) {
        List<EnvelopeDTO> result = new ArrayList<>();
        result.addAll(getAllSpendings(token));
        result.addAll(getAllGoals(token));
        return result;
    }

    @Override
    public List<EnvelopeDTO> getActiveEnvelopesByToken(String token) {
        return getAllEnvelopesByToken(token).stream().filter(EnvelopeDTO::isActive).toList();
    }

    @Override
    public List<SpendingDTO> getAllSpendings(String token) {
        Account account = accountService.getAccountEntityFromToken(token);
        return envelopeMapper.toSpendingDTOs(spendingRepository.getEnvelopesByAccountID(account.getID()));
    }

    @Override
    public List<SpendingDTO> getActiveSpendings(String token) {
        return getAllSpendings(token).stream().filter(SpendingDTO::isActive).toList();
    }

    @Override
    public List<GoalDTO> getAllGoals(String token) {
        Account account = accountService.getAccountEntityFromToken(token);
        return envelopeMapper.toGoalDTOs(goalRepository.getEnvelopesByAccountID(account.getID()));
    }

    @Override
    public List<GoalDTO> getActiveGoals(String token) {
        return getAllGoals(token).stream().filter(GoalDTO::isActive).toList();
    }

    @Override
    public EnvelopeDTO getEnvelopeByID(String token, Long envelopeID) {
        Envelope envelope = envelopeRepositoryFacade.getEnvelopeByID(envelopeID);
        checkOwnership(token, envelope);
        if(envelope instanceof Spending){
            return envelopeMapper.toSpendingDTO((Spending) envelope);
        } else if (envelope instanceof Goal){
            return envelopeMapper.toGoalDTO((Goal) envelope);
        }
        return null;
    }

    @Override
    public EnvelopeDTO updateEnvelopeByID(String token, Long envelopeID, EnvelopeDTO envelopeDTO) {
        Envelope envelope = envelopeRepositoryFacade.getEnvelopeByID(envelopeID);

        checkOwnership(token, envelope);

        if(envelopeDTO instanceof SpendingDTO){
            envelopeMapper.updateSpending((SpendingDTO) envelopeDTO, (Spending) envelope);
            return envelopeMapper.toSpendingDTO((Spending) envelope);
        } else if (envelopeDTO instanceof GoalDTO){
            envelopeMapper.updateGoal((GoalDTO) envelopeDTO, (Goal) envelope);
            return envelopeMapper.toGoalDTO((Goal) envelope);
        } else {
            log.error("Updating unknown envelope type");
            throw ExceptionController.internalServerError("Unknown envelope type", "ENVELOPE_UPDATE_UNKNOWN_TYPE");
        }
    }

    @Override
    public void deleteEnvelopeByID(String token, Long envelopeID) {
        Envelope envelope = envelopeRepositoryFacade.getEnvelopeByID(envelopeID);
        checkOwnership(token, envelope);
        envelopeRepositoryFacade.deleteEnvelopeByID(envelopeID);
    }

    @Override
    public List<EnvelopeDTO> getEnvelopesWithNegativeBalance(String token) {
//        List<EnvelopeDTO> list = getAllEnvelopesByToken(token);
//        return list.stream().filter(e -> e.get().compareTo(BigDecimal.ZERO) < 0).toList();
        return null;
    }

    @Override
    public List<EnvelopeDTO> getEnvelopesBelowPercentBalance(String token, Long percentage) {
//        if(percentage < 0 || percentage > 100){
//            throw ExceptionController.badRequest("Percentage must be between 0 and 100", "ILLEGAL_ARGUMENT");
//        }
//        List<EnvelopeDTO> list = getAllEnvelopesByToken(token);
//        return list.stream().filter(e -> e.getCurrentBalance().compareTo(e.getBudgetAmount().multiply(BigDecimal.valueOf((float)percentage/100))) < 0).toList();
        return null;
    }

    @Override
    public EnvelopeWithTransactionsDTO getEnvelopeWithTransactions(String token, Long envelopeID) {
//        EnvelopeDTO envelopeDTO = getEnvelopeByID(token, envelopeID);
//        List<EnvelopeTransactionDTO> envelopeTransactions = transactionService.getAllEnvelopeTransactions(token).stream()
//                .filter(e -> e.getEnvelopeID().equals(envelopeID)).toList();
//        return new EnvelopeWithTransactionsDTO(envelopeDTO, envelopeTransactions);
        return null;
    }

    @Override
    public List<EnvelopeWithTransactionsDTO> getFullEnvelopesBetweenMonths(String token, LocalDate startDate, LocalDate endDate) {
//        List<EnvelopeWithTransactionsDTO> result = new ArrayList<>();
//        List<EnvelopeDTO> envelopes = getAllEnvelopesByToken(token);
//        for(EnvelopeDTO envelope : envelopes){
//            List<TransactionDTO> envelopeTransactions = transactionService.getEnvelopeTransactionsBetweenMonths(token, startDate, endDate).stream()
//                    .filter(e -> e.getEnvelopeID().equals(envelope.getID())).toList();
//            result.add(new EnvelopeWithTransactionsDTO(envelope, envelopeTransactions));
//        }
//        return result;
        return null;
    }

    @Override
    public List<EnvelopeWithTransactionsDTO> getTopNEnvelopesBySpending(String token, int n, LocalDate startDate, LocalDate endDate) {
//        List<EnvelopeWithTransactionsDTO> list = getFullEnvelopesBetweenMonths(token, startDate, endDate);
//        for (EnvelopeWithTransactionsDTO envelopeWithTransactionsDTO : list) {
//            envelopeWithTransactionsDTO.setTransactions(envelopeWithTransactionsDTO.getTransactions().stream().filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) < 0).toList());
//        }
//        return list.stream().sorted(Comparator.comparing(e -> e.getTransactions().stream().mapToInt(t -> t.getAmount().intValue()).sum())).limit(n).toList();
        return null;
    }

    @Override
    public List<GoalDTO> getGoalsUnmet(String token) {
        return null;
    }

    @Override
    public List<String> calculateUnmetGoalsAllocations(String token) {
        return null;
    }

    private void checkOwnership(String token, Envelope envelope) {
        Account account = accountService.getAccountEntityFromToken(token);
        if(!envelope.getAccount().getID().equals(account.getID())){
            throw ExceptionController.forbidden();
        }
    }
}
