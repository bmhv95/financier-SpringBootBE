package com.personal.project.repository;

import com.personal.project.entity.GoalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalTransactionRepository extends JpaRepository<GoalTransaction, Long> {
}
