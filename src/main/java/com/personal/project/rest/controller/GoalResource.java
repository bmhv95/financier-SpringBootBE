package com.personal.project.rest.controller;

import com.personal.project.rest.GoalAPI;
import com.personal.project.service.DTO.GoalDTO;
import com.personal.project.service.GoalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GoalResource implements GoalAPI {
    private final GoalService goalService;
    @Override
    public ResponseEntity<GoalDTO> createNewGoal(String token, GoalDTO goalDTO) {
        GoalDTO newGoal = goalService.createNewGoal(token, goalDTO);
        return ResponseEntity.created(URI.create("/api/goals/" + newGoal.getGoalID())).body(newGoal);
    }

    @Override
    public ResponseEntity<List<GoalDTO>> getAllGoalsByToken(String token) {
        return ResponseEntity.ok(goalService.getAllGoalsByToken(token));
    }

    @Override
    public ResponseEntity<GoalDTO> getGoalById(String token, Long id) {
        return ResponseEntity.ok(goalService.getGoalByID(token, id));
    }

    @Override
    public ResponseEntity<GoalDTO> updateGoalById(String token, Long id, GoalDTO goalDTO) {
        return ResponseEntity.ok(goalService.updateGoalByID(token, id, goalDTO));
    }

    @Override
    public ResponseEntity<Void> deleteGoalById(String token, Long id) {
        goalService.deleteGoalByID(token, id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<GoalDTO>> getGoalsUnmet(String token) {
        return ResponseEntity.ok(goalService.getGoalsUnmet(token));
    }

    @Override
    public ResponseEntity<List<String>> calculateUnmetGoalsAllocations(String token) {
        return ResponseEntity.ok(goalService.calculateUnmetGoalsAllocations(token));
    }


}
