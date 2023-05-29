package com.personal.project.repository;

import com.personal.project.entity.Account;
import com.personal.project.entity.GoalTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface GoalTransactionRepository extends TransactionRepository<GoalTransaction> {
    @Override
    @Query("SELECT t FROM GoalTransaction t WHERE t.wallet.account = ?1")
    List<GoalTransaction> findAllByAccount(Account account);
}
