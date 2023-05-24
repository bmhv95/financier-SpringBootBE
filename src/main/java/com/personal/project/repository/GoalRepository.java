package com.personal.project.repository;

import com.personal.project.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    @Query("SELECT g FROM Goal g WHERE g.account.accountID = ?1")
    List<Goal> getGoalsByAccountID(Long accountID);
}
