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
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionID;

    private LocalDate transactionDate;

    @ManyToOne
    @JoinColumn(name = "envelope_id")
    private Envelope envelope;

    @OneToMany(mappedBy = "transaction")
    private List<TransactionDetail> transactionDetails;
}
