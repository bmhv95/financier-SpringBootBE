package com.personal.project.listener;

import com.personal.project.entity.Transaction;
import jakarta.persistence.*;

import java.math.BigDecimal;

public class TransactionListener {
    private Transaction previousTransaction;

    @PostLoad
    public void setPreviousTransaction(Transaction transaction){
        previousTransaction = Transaction.builder()
                .ID(transaction.getID())
                .amount(transaction.getAmount())
                .wallet(transaction.getWallet())
                .envelope(transaction.getEnvelope())
                .build();
    }

    @PreRemove
    public void removeTransaction(Transaction transaction){
        addTransactionWallet(previousTransaction);
        subTransactionEnvelope(previousTransaction);
    }

    @PostPersist
    public void persistTransaction(Transaction transaction){
        addTransactionEnvelope(transaction);
        subTransactionWallet(transaction);
    }

    @PreUpdate
    public void updateTransaction(Transaction transaction){
        if (transaction.getWallet() != null || transaction.getAmount() != null){
            subTransactionEnvelope(previousTransaction);
            addTransactionWallet(previousTransaction);
            addTransactionEnvelope(transaction);
            subTransactionWallet(transaction);
        }
    }

    private void subTransactionWallet(Transaction transaction){
        BigDecimal walletBalance = transaction.getWallet().getBalance();
        BigDecimal newBalance = walletBalance.subtract(transaction.getAmount());
        transaction.getWallet().setBalance(newBalance);
    }

    private void addTransactionEnvelope(Transaction transaction){
        BigDecimal envelopeBalance = transaction.getEnvelope().getSpent();
        BigDecimal newBalance = envelopeBalance.add(transaction.getAmount());
        transaction.getEnvelope().setSpent(newBalance);
    }

    private void addTransactionWallet(Transaction transaction){
        BigDecimal walletBalance = transaction.getWallet().getBalance();
        BigDecimal newBalance = walletBalance.add(transaction.getAmount());
        transaction.getWallet().setBalance(newBalance);
    }

    private void subTransactionEnvelope(Transaction transaction){
        BigDecimal envelopeBalance = transaction.getEnvelope().getSpent();
        BigDecimal newBalance = envelopeBalance.subtract(transaction.getAmount());
        transaction.getEnvelope().setSpent(newBalance);
    }
}
