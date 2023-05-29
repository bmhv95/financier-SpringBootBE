package com.personal.project.service.impl;

import com.personal.project.entity.Account;
import com.personal.project.entity.Goal;
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
        checkDTO(goalDTO);

        Account account = accountService.getAccountEntityFromToken(token);

        Goal goal = Goal.builder()
                .goalName(goalDTO.getGoalName())
                .goalAmount(goalDTO.getGoalAmount())
                .goalCurrentBalance(goalDTO.getGoalCurrentBalance() == null ? BigDecimal.ZERO : goalDTO.getGoalCurrentBalance())
                .goalEndDate(goalDTO.getGoalEndDate())
                .account(account)
                .build();

        if(goalDTO.getGoalStartDate() != null){
            goal.setGoalStartDate(goalDTO.getGoalStartDate());
        }

        return goalMapper.goalToGoalDTO(goalRepository.save(goal));
    }

    @Override
    public List<GoalDTO> getAllGoalsByToken(String token) {
        return goalMapper.goalListToGoalDTOList(
                goalRepository.getGoalsByAccountID(accountService.getAccountEntityFromToken(token).getAccountID())
        );
    }

    @Override
    public GoalDTO getGoalByID(String token, Long goalID) {
        checkOwnership(token, goalID);
        return goalMapper.goalToGoalDTO(
                goalRepository.findById(goalID).get()
        );
    }

    @Override
    public GoalDTO updateGoalByID(String token, Long goalID, GoalDTO goalDTO) {
        checkOwnership(token, goalID);
        checkDTO(goalDTO);

        Goal goal = goalRepository.findById(goalID).get();

        goalMapper.updateGoal(goalDTO, goal);

        return goalMapper.goalToGoalDTO(
                goalRepository.save(goal)
        );
    }

    @Override
    public void deleteGoalByID(String token, Long goalID) {
        checkOwnership(token, goalID);
        goalRepository.deleteById(goalID);
    }

    @Override
    public List<GoalDTO> getGoalsUnmet(String token) {
        return getAllGoalsByToken(token).stream()
                .filter(goal -> goal.getGoalCurrentBalance().compareTo(goal.getGoalAmount()) < 0)
                .toList();
    }

    @Override
    public List<String> calculateUnmetGoalsAllocations(String token) {
        List<String> result = new ArrayList<>();
        List<GoalDTO> unmetGoals = getGoalsUnmet(token);
        for(GoalDTO goal : unmetGoals){
            long timeRemaining = LocalDate.now().until(goal.getGoalEndDate(), ChronoUnit.MONTHS);
            BigDecimal monthlyAllocations = goal.getGoalAmount().subtract(goal.getGoalCurrentBalance()).divide(BigDecimal.valueOf(timeRemaining), RoundingMode.DOWN);
            result.add("Goal: " + goal.getGoalID() + " - " + goal.getGoalName() + " - Monthly allocations: " + monthlyAllocations);
        }
        return result;
    }

    private void checkOwnership(String token, Long goalID) {
        Account account = accountService.getAccountEntityFromToken(token);
        Goal goal = goalRepository.findById(goalID).orElseThrow(() -> new RuntimeException("Goal not found"));
        if(!goal.getAccount().getAccountID().equals(account.getAccountID())) {
            throw new RuntimeException("You are not the owner of this goal");
        }
    }

    private void checkDTO(GoalDTO goalDTO){
        return;
    }
}
