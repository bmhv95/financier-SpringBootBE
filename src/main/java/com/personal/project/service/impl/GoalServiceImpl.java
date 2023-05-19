package com.personal.project.service.impl;

import com.personal.project.entity.Goal;
import com.personal.project.repository.GoalRepository;
import com.personal.project.service.DTO.GoalDTO;
import com.personal.project.service.GoalService;
import com.personal.project.service.mapper.GoalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;

    @Override
    public GoalDTO createNewGoal(GoalDTO goalDTO) {
        checkDTO(goalDTO);

        Goal newGoal = Goal.builder()
                .goalName(goalDTO.getGoalName())
                .goalAmount(goalDTO.getGoalAmount())
                .goalStartDate(goalDTO.getGoalStartDate())
                .goalEndDate(goalDTO.getGoalEndDate())
                .goalCurrentBalance(BigDecimal.valueOf(0))
                .build();

        return goalMapper.goalToGoalDTO(goalRepository.save(newGoal));
    }

    @Override
    public List<GoalDTO> getAllGoals() {
        return null;
    }

    @Override
    public GoalDTO getGoalByID(Long goalID) {
        return null;
    }

    @Override
    public GoalDTO updateGoalByID(Long goalID, GoalDTO goalDTO) {
        return null;
    }

    @Override
    public void deleteGoalByID(Long goalID) {

    }

    private void checkDTO(GoalDTO goalDTO) {
        if (goalDTO == null) {
            throw new RuntimeException("GoalDTO is null");
        }
    }
}
