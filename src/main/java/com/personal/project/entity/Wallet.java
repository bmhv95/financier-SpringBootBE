package com.personal.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long walletID;

    private String walletName;

    @Column(nullable = false)
    private BigDecimal walletBalance;

    @CreationTimestamp
    private LocalDate createdDate;

    @UpdateTimestamp
    private LocalDate updatedDate;

    @ManyToOne
    @JoinColumn(name = "acc_id")
    private Account account;
}
