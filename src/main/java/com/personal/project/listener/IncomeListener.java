package com.personal.project.listener;

import com.personal.project.entity.Income;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.math.BigDecimal;

public class IncomeListener {
    private Income previousIncome;

    @PreUpdate
    @PrePersist
    public void updateIncome(Income income) {
        updateIncomeMoney(income);
        updateIncomeWallet(income);
    }

    public void updateIncomeMoney(Income income) {
        if (income.getAmount() != null) {
            BigDecimal walletBalance = income.getWallet().getBalance();
            BigDecimal newBalance = walletBalance.add(income.getAmount());
            income.getWallet().setBalance(newBalance);
        }
    }

    public void updateIncomeWallet(Income income) {
        if (previousIncome != null && !previousIncome.getWallet().equals(income.getWallet())) {
            BigDecimal previousWalletBalance = previousIncome.getWallet().getBalance();
            BigDecimal newBalance = previousWalletBalance.subtract(income.getAmount());
            previousIncome.getWallet().setBalance(newBalance);

            income.getWallet().setBalance(
                    income.getWallet().getBalance().add(income.getAmount())
            );
        }
    }
}
