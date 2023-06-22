package com.personal.project.listener;

import com.personal.project.entity.Income;
import com.personal.project.entity.Wallet;
import com.personal.project.repository.IncomeRepository;
import com.personal.project.repository.WalletRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public class IncomeListener {
    private Income previousIncome;
    @PostLoad
    public void postLoadIncome(Income income) {
        this.previousIncome = Income.builder()
                .ID(income.getID())
                .amount(income.getAmount())
                .wallet(income.getWallet())
                .build();
    }


    @PreRemove
    public void removeIncome(Income income) {
        subtractWalletMoney(previousIncome);
    }

    @PreUpdate
    public void updateIncome(Income income){
        if(income.getWallet() != null || income.getAmount() != null){
            subtractWalletMoney(previousIncome);
            updateIncomeWallet(income);
        }
    }

    @PostPersist
    public void persistIncome(Income income) {
        updateIncomeWallet(income);
    }

    private void subtractWalletMoney(Income income) {
        BigDecimal walletBalance = income.getWallet().getBalance();
        BigDecimal newBalance = walletBalance.subtract(income.getAmount());
        income.getWallet().setBalance(newBalance);
    }

    private void updateIncomeWallet(Income income) {
        BigDecimal walletBalance = income.getWallet().getBalance();
        BigDecimal newBalance = walletBalance.add(income.getAmount());
        income.getWallet().setBalance(newBalance);
    }
}
