package com.personal.project.service;

import com.personal.project.service.DTO.GoalDTO;

import java.util.List;

public interface GoalService {

    GoalDTO createNewGoal(GoalDTO goalDTO);

    List<GoalDTO> getAllGoals();

    GoalDTO getGoalByID(Long goalID);

    GoalDTO updateGoalByID(Long goalID, GoalDTO goalDTO);

    void deleteGoalByID(Long goalID);
}
