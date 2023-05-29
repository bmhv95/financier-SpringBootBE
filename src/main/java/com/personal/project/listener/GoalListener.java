package com.personal.project.listener;

import com.personal.project.entity.GoalTransaction;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.math.BigDecimal;

public class GoalListener {
    private GoalTransaction previousTransaction;

    @PreUpdate
    @PrePersist
    public void updateTransaction(GoalTransaction transaction) {
        updateGoalMoney(transaction);
        updateGoalTarget(transaction);
    }

    private void updateGoalMoney(GoalTransaction transaction) {
        if(transaction.getTransactionAmount() != null) {
            BigDecimal goalBalance = transaction.getGoal().getGoalCurrentBalance();
            BigDecimal newBalance = goalBalance.add(transaction.getTransactionAmount());
            transaction.getGoal().setGoalCurrentBalance(newBalance);
        }
    }

    private void updateGoalTarget(GoalTransaction transaction) {
        if(previousTransaction != null && !previousTransaction.getGoal().equals(transaction.getGoal())) {
            BigDecimal previousGoalBalance = previousTransaction.getGoal().getGoalCurrentBalance();
            BigDecimal newBalance = previousGoalBalance.subtract(transaction.getTransactionAmount());
            previousTransaction.getGoal().setGoalCurrentBalance(newBalance);

            transaction.getGoal().setGoalCurrentBalance(
                    transaction.getGoal().getGoalCurrentBalance().add(transaction.getTransactionAmount())
            );
        }
    }
}
