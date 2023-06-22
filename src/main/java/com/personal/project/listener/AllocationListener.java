package com.personal.project.listener;

import com.personal.project.entity.Allocation;
import jakarta.persistence.*;

import java.math.BigDecimal;

public class AllocationListener {
    private Allocation previousAllocation;

    @PostLoad
    public void setPreviousAllocation(Allocation allocation){
        this.previousAllocation = Allocation.builder()
                .ID(allocation.getID())
                .amount(allocation.getAmount())
                .envelope(allocation.getEnvelope())
                .wallet(allocation.getWallet())
                .build();
    }

    @PreRemove
    public void removeAllocation(Allocation allocation){
        subtractEnvelopeAllocation(previousAllocation);
    }

    @PostPersist
    public void persistAllocation(Allocation allocation){
        addEnvelopeAllocation(allocation);
    }

    @PreUpdate
    public void updateAllocation(Allocation allocation){
        if(allocation.getWallet() != null || allocation.getAmount() != null){
            subtractEnvelopeAllocation(previousAllocation);
            addEnvelopeAllocation(allocation);
        }
    }

    private void addEnvelopeAllocation(Allocation allocation){
        BigDecimal envelopeAllocation = allocation.getEnvelope().getAllocated();
        BigDecimal newAllocation = envelopeAllocation.add(allocation.getAmount());
        allocation.getEnvelope().setAllocated(newAllocation);
    }

    private void subtractEnvelopeAllocation(Allocation allocation){
        BigDecimal envelopeAllocation = allocation.getEnvelope().getAllocated();
        BigDecimal newAllocation = envelopeAllocation.subtract(allocation.getAmount());
        allocation.getEnvelope().setAllocated(newAllocation);
    }
}
