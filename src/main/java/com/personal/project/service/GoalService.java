package com.personal.project.service;

import com.personal.project.service.DTO.GoalDTO;

import java.util.List;

public interface GoalService {

    GoalDTO createNewGoal(String token, GoalDTO goalDTO);

    List<GoalDTO> getAllGoalsByToken(String token);

    GoalDTO getGoalByID(String token, Long goalID);

    GoalDTO updateGoalByID(String token, Long goalID, GoalDTO goalDTO);

    void deleteGoalByID(String token, Long goalID);

    List<GoalDTO> getGoalsUnmet(String token);

    List<String> calculateUnmetGoalsAllocations(String token);
}
