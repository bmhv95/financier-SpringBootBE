package com.personal.project.listener;

import com.personal.project.entity.EnvelopeTransaction;
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
        if(transaction.getTransactionAmount() != null) {
            BigDecimal walletBalance = transaction.getWallet().getWalletBalance();
            BigDecimal newBalance = walletBalance.add(transaction.getTransactionAmount());
            transaction.getWallet().setWalletBalance(newBalance);
        }
    }

    private void updateTransactionWallet(Transaction transaction) {
        if(previousTransaction != null && !previousTransaction.getWallet().equals(transaction.getWallet())) {
            BigDecimal previousWalletBalance = previousTransaction.getWallet().getWalletBalance();
            BigDecimal newBalance = previousWalletBalance.subtract(transaction.getTransactionAmount());
            previousTransaction.getWallet().setWalletBalance(newBalance);

            transaction.getWallet().setWalletBalance(
                    transaction.getWallet().getWalletBalance().add(transaction.getTransactionAmount())
            );
        }
    }
}
