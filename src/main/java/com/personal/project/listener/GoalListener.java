package com.personal.project.listener;

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
        if(transaction.getAmount() != null) {
            BigDecimal goalBalance = transaction.getGoal().getCurrentBalance();
            BigDecimal newBalance = goalBalance.add(transaction.getAmount());
            transaction.getGoal().setCurrentBalance(newBalance);
        }
    }

    private void updateGoalTarget(GoalTransaction transaction) {
        if(previousTransaction != null && !previousTransaction.getGoal().equals(transaction.getGoal())) {
            BigDecimal previousGoalBalance = previousTransaction.getGoal().getCurrentBalance();
            BigDecimal newBalance = previousGoalBalance.subtract(transaction.getAmount());
            previousTransaction.getGoal().setCurrentBalance(newBalance);

            transaction.getGoal().setCurrentBalance(
                    transaction.getGoal().getCurrentBalance().add(transaction.getAmount())
            );
        }
    }
}
