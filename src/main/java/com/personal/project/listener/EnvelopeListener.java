package com.personal.project.listener;

import com.personal.project.entity.EnvelopeTransaction;
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
        if(transaction.getTransactionAmount() != null) {
            BigDecimal envelopeBalance = transaction.getEnvelope().getEnvelopeCurrentBalance();
            BigDecimal newBalance = envelopeBalance.add(transaction.getTransactionAmount());
            transaction.getEnvelope().setEnvelopeCurrentBalance(newBalance);
        }
    }

    private void updateEnvelopeTarget(EnvelopeTransaction transaction) {
        if(previousTransaction != null && !previousTransaction.getEnvelope().equals(transaction.getEnvelope())) {
            BigDecimal previousEnvelopeBalance = previousTransaction.getEnvelope().getEnvelopeCurrentBalance();
            BigDecimal newBalance = previousEnvelopeBalance.subtract(transaction.getTransactionAmount());
            previousTransaction.getEnvelope().setEnvelopeCurrentBalance(newBalance);

            transaction.getEnvelope().setEnvelopeCurrentBalance(
                    transaction.getEnvelope().getEnvelopeCurrentBalance().add(transaction.getTransactionAmount())
            );
        }
    }
}
