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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"goal_transaction_id", "wallet_id"})})
public class GoalTransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_transaction_detail_id")
    private Long goalTransactionDetailID;

    private BigDecimal goalTransactionAmount;
    private String goalTransactionDescription;

    @ManyToOne
    @JoinColumn(name = "goal_transaction_id")
    private GoalTransaction goalTransaction;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}
