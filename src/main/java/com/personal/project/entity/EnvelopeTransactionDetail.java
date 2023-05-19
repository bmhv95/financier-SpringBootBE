package com.personal.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"envelope_transaction_id", "wallet_id"})})
public class EnvelopeTransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "envelope_transaction_detail_id")
    private Long envelopeTransactionDetailID;

    private BigDecimal envelopeTransactionAmount;
    private String envelopeTransactionDescription;

    @ManyToOne
    @JoinColumn(name = "envelope_transaction_id")
    private EnvelopeTransaction envelopeTransaction;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}
