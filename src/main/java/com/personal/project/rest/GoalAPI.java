package com.personal.project.rest;

import com.personal.project.service.DTO.GoalDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/goals")
public interface GoalAPI {
    @PostMapping("/new")
    ResponseEntity<GoalDTO> createNewGoal(GoalDTO goalDTO);

    @GetMapping("/{id}")
    ResponseEntity<GoalDTO> getGoalById(Long id);

    @PutMapping("/{id}")
    ResponseEntity<GoalDTO> updateGoalById(Long id, GoalDTO goalDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteGoalById(Long id);
}
