package com.personal.project.service.impl;

import com.personal.project.entity.Account;
import com.personal.project.entity.Goal;
import com.personal.project.exception.ExceptionController;
import com.personal.project.repository.GoalRepository;
import com.personal.project.service.AccountService;
import com.personal.project.service.DTO.GoalDTO;
import com.personal.project.service.GoalService;
import com.personal.project.service.mapper.GoalMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoalServiceImpl implements GoalService{
    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;
    private final AccountService accountService;
    @Override
    public GoalDTO createNewGoal(String token, GoalDTO goalDTO) {
        Account account = accountService.getAccountEntityFromToken(token);

        if((goalDTO.getEndDate() != null && goalDTO.getEndDate().isBefore(LocalDate.now()))
                || (goalDTO.getStartDate() != null &&  goalDTO.getEndDate() != null && goalDTO.getStartDate().isBefore(goalDTO.getEndDate()))) {
            log.error("Date input error");
            throw ExceptionController.badRequest("Date input error", "GOAL_DATE_ERROR");
        }

        Goal goal = Goal.builder()
                .name(goalDTO.getName())
                .amount(goalDTO.getAmount())
                .currentBalance(goalDTO.getCurrentBalance() == null ? BigDecimal.ZERO : goalDTO.getCurrentBalance())
                .startDate(goalDTO.getStartDate() == null ? LocalDate.now() : goalDTO.getStartDate())
                .endDate(goalDTO.getEndDate())
                .account(account)
                .build();

        return goalMapper.goalToGoalDTO(goalRepository.save(goal));
    }

    @Override
    public List<GoalDTO> getAllGoalsByToken(String token) {
        return goalMapper.goalListToGoalDTOList(
                goalRepository.getGoalsByAccountID(accountService.getAccountEntityFromToken(token).getID())
        );
    }

    @Override
    public GoalDTO getGoalByID(String token, Long goalID) {
        Goal goal = goalRepository.findById(goalID).orElseThrow(() -> ExceptionController.goalNotFound(goalID));
        checkOwnership(token, goal);
        return goalMapper.goalToGoalDTO(goal);
    }

    @Override
    public GoalDTO updateGoalByID(String token, Long goalID, GoalDTO goalDTO) {
        Goal goal = goalRepository.findById(goalID).orElseThrow(() -> ExceptionController.goalNotFound(goalID));

        checkOwnership(token, goal);

        goalMapper.updateGoal(goalDTO, goal);

        return goalMapper.goalToGoalDTO(
                goalRepository.save(goal)
        );
    }

    @Override
    public void deleteGoalByID(String token, Long goalID) {
        Goal goal = goalRepository.findById(goalID).orElseThrow(() -> ExceptionController.goalNotFound(goalID));
        checkOwnership(token, goal);
        goalRepository.deleteById(goalID);
    }

    @Override
    public List<GoalDTO> getGoalsUnmet(String token) {
        return getAllGoalsByToken(token).stream()
                .filter(goal -> goal.getCurrentBalance().compareTo(goal.getAmount()) < 0)
                .toList();
    }

    @Override
    public List<String> calculateUnmetGoalsAllocations(String token) {
        List<String> result = new ArrayList<>();
        List<GoalDTO> unmetGoals = getGoalsUnmet(token);
        for(GoalDTO goal : unmetGoals){
            long timeRemaining = LocalDate.now().until(goal.getEndDate(), ChronoUnit.MONTHS);
            BigDecimal monthlyAllocations = goal.getAmount().subtract(goal.getCurrentBalance()).divide(BigDecimal.valueOf(timeRemaining), RoundingMode.DOWN);
            result.add("Goal: " + goal.getID() + " - " + goal.getName() + " - Monthly allocations: " + monthlyAllocations);
        }
        return result;
    }

    private void checkOwnership(String token, Goal goal) {
        Account account = accountService.getAccountEntityFromToken(token);
        if(!goal.getAccount().getID().equals(account.getID())) {
            throw ExceptionController.forbidden();
        }
    }
}
