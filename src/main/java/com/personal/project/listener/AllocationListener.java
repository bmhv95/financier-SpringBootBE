package com.personal.project.listener;

import com.personal.project.entity.Allocation;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.math.BigDecimal;

public class AllocationListener {
    private Allocation previousAllocation;

    @PreUpdate
    @PrePersist
    public void updateAllocation(Allocation allocation) {
        updateAllocationMoney(allocation);
        updateAllocationEnvelope(allocation);
    }

    private void updateAllocationMoney(Allocation allocation) {
        if (allocation.getAmount() != null) {
            BigDecimal envelopeBalance = allocation.getEnvelope().getCurrentBalance();
            BigDecimal newBalance = envelopeBalance.add(allocation.getAmount());
            allocation.getWallet().setBalance(newBalance);
        }
    }

    private void updateAllocationWallet(Allocation allocation) {
        if(previousAllocation != null && !previousAllocation.getWallet().equals(allocation.getWallet())) {
            BigDecimal previousWalletBalance = previousAllocation.getWallet().getBalance();
            BigDecimal newBalance = previousWalletBalance.subtract(allocation.getAmount());
            previousAllocation.getWallet().setBalance(newBalance);

            allocation.getWallet().setBalance(
                    allocation.getWallet().getBalance().add(allocation.getAmount())
            );
        }
    }

    private void updateAllocationEnvelope(Allocation allocation) {
        if(previousAllocation != null && !previousAllocation.getEnvelope().equals(allocation.getEnvelope())) {
            BigDecimal previousEnvelopeBalance = previousAllocation.getEnvelope().getCurrentBalance();
            BigDecimal newBalance = previousEnvelopeBalance.subtract(allocation.getAmount());
            previousAllocation.getEnvelope().setCurrentBalance(newBalance);

            allocation.getEnvelope().setCurrentBalance(
                    allocation.getEnvelope().getCurrentBalance().add(allocation.getAmount())
            );
        }
    }
}
