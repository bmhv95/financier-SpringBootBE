package com.personal.project.entity;

import com.personal.project.listener.TransactionListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("ENVELOPE")
@EntityListeners(TransactionListener.class)
public class EnvelopeTransaction extends Transaction {
    @ManyToOne
    @JoinColumn(name = "envelope_id")
    private Envelope envelope;

    @PostPersist
    public void updateEnvelope() {
        envelope.setEnvelopeCurrentBalance(envelope.getEnvelopeCurrentBalance().add(super.getTransactionAmount()));
    }
}
