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
        if (income.getIncomeAmount() != null) {
            BigDecimal walletBalance = income.getWallet().getWalletBalance();
            BigDecimal newBalance = walletBalance.add(income.getIncomeAmount());
            income.getWallet().setWalletBalance(newBalance);
        }
    }

    public void updateIncomeWallet(Income income) {
        if (previousIncome != null && !previousIncome.getWallet().equals(income.getWallet())) {
            BigDecimal previousWalletBalance = previousIncome.getWallet().getWalletBalance();
            BigDecimal newBalance = previousWalletBalance.subtract(income.getIncomeAmount());
            previousIncome.getWallet().setWalletBalance(newBalance);

            income.getWallet().setWalletBalance(
                    income.getWallet().getWalletBalance().add(income.getIncomeAmount())
            );
        }
    }
}
