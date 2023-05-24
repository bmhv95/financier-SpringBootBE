package com.personal.project.rest;

import com.personal.project.service.DTO.GoalDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/goals")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public interface GoalAPI {
    @PostMapping("/new")
    ResponseEntity<GoalDTO> createNewGoal(@RequestHeader("Authorization") String token, @Valid @RequestBody GoalDTO goalDTO);

    @GetMapping
    ResponseEntity<List<GoalDTO>> getAllGoalsByToken(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    ResponseEntity<GoalDTO> getGoalById(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<GoalDTO> updateGoalById(@RequestHeader("Authorization") String token, @PathVariable Long id, @Valid @RequestBody GoalDTO goalDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteGoalById(@RequestHeader("Authorization") String token, @PathVariable Long id);
}
