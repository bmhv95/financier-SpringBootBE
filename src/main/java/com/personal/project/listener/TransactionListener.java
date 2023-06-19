package com.personal.project.listener;

import com.personal.project.entity.Transaction;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.math.BigDecimal;

public class TransactionListener {
    private Transaction previousTransaction;

    @PreUpdate
    @PrePersist
    public void updateTransaction(Transaction transaction) {
        updateWalletMoney(transaction);
        updateTransactionWallet(transaction);
    }
    private void updateWalletMoney(Transaction transaction) {
        if(transaction.getAmount() != null) {
            BigDecimal walletBalance = transaction.getWallet().getBalance();
            BigDecimal newBalance = walletBalance.subtract(transaction.getAmount());
            transaction.getWallet().setBalance(newBalance);
        }
    }

    private void updateTransactionWallet(Transaction transaction) {
        if(previousTransaction != null && !previousTransaction.getWallet().equals(transaction.getWallet())) {
            BigDecimal previousWalletBalance = previousTransaction.getWallet().getBalance();
            BigDecimal newBalance = previousWalletBalance.add(transaction.getAmount());
            previousTransaction.getWallet().setBalance(newBalance);

            transaction.getWallet().setBalance(
                    transaction.getWallet().getBalance().subtract(transaction.getAmount())
            );
        }
    }
}
