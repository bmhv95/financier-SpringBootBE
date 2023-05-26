package com.personal.project.repository;

import com.personal.project.entity.GoalTransaction;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GoalTransactionRepository extends TransactionRepository<GoalTransaction> {
}
