package com.personal.project.rest.controller;

import com.personal.project.rest.GoalAPI;
import com.personal.project.service.DTO.GoalDTO;
import com.personal.project.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoalResource implements GoalAPI {
    private final GoalService goalService;

    @Override
    public ResponseEntity<GoalDTO> createNewGoal(GoalDTO goalDTO) {
        GoalDTO newGoal = goalService.createNewGoal(goalDTO);
        return ResponseEntity.created(URI.create("/api/goals/" + newGoal.getGoalID())).body(newGoal);
    }

    @Override
    public ResponseEntity<List<GoalDTO>> getAllGoals() {
        return ResponseEntity.ok(goalService.getAllGoals());
    }

    @Override
    public ResponseEntity<GoalDTO> getGoalById(Long id) {
        return ResponseEntity.ok(goalService.getGoalByID(id));
    }

    @Override
    public ResponseEntity<GoalDTO> updateGoalById(Long id, GoalDTO goalDTO) {
        return ResponseEntity.ok(goalService.updateGoalByID(id, goalDTO));
    }

    @Override
    public ResponseEntity<Void> deleteGoalById(Long id) {
        goalService.deleteGoalByID(id);
        return ResponseEntity.noContent().build();
    }
}
