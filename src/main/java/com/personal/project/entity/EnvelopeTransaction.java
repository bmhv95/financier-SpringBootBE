package com.personal.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvelopeTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "envelope_transaction_id")
    private Long envelopeTransactionID;

    private LocalDate envelopeTransactionDate;

    @ManyToOne
    @JoinColumn(name = "envelope_id")
    private Envelope envelope;

    @OneToMany(mappedBy = "envelopeTransaction")
    private List<EnvelopeTransactionDetail> envelopeTransactionDetails;
}
