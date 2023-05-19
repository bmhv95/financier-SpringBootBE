package com.personal.project.repository;

import com.personal.project.entity.GoalTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalTransactionDetailRepository extends JpaRepository<GoalTransactionDetail, Long> {
}
