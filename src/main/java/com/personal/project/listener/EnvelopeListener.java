package com.personal.project.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.math.BigDecimal;

public class EnvelopeListener {
    private EnvelopeTransaction previousTransaction;

    @PreUpdate
    @PrePersist
    public void updateTransaction(EnvelopeTransaction transaction) {
        updateEnvelopeMoney(transaction);
        updateEnvelopeTarget(transaction);
    }

    private void updateEnvelopeMoney(EnvelopeTransaction transaction) {
        if(transaction.getAmount() != null) {
            BigDecimal envelopeBalance = transaction.getEnvelope().getCurrentBalance();
            BigDecimal newBalance = envelopeBalance.add(transaction.getAmount());
            transaction.getEnvelope().setCurrentBalance(newBalance);
        }
    }

    private void updateEnvelopeTarget(EnvelopeTransaction transaction) {
        if(previousTransaction != null && !previousTransaction.getEnvelope().equals(transaction.getEnvelope())) {
            BigDecimal previousEnvelopeBalance = previousTransaction.getEnvelope().getCurrentBalance();
            BigDecimal newBalance = previousEnvelopeBalance.subtract(transaction.getAmount());
            previousTransaction.getEnvelope().setCurrentBalance(newBalance);

            transaction.getEnvelope().setCurrentBalance(
                    transaction.getEnvelope().getCurrentBalance().add(transaction.getAmount())
            );
        }
    }
}
