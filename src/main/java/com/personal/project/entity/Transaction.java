package com.personal.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@NoArgsConstructor
@AllArgsConstructor
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionID;

    @Column(nullable = false)
    private BigDecimal transactionAmount;
    private String transactionName;
    private String transactionComment;

    @CreationTimestamp
    private LocalDate transactionDate;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;
}
