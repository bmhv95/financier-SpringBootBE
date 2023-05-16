package com.personal.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Envelope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "envelope_id")
    private Long envelopeID;

    private String envelopeName;
    private BigDecimal envelopeBudgetAmount;
    private BigDecimal envelopeCurrentBalance;

    private LocalDate envelopeDate;

    @ManyToOne
    @JoinColumn(name = "acc_id")
    private Account account;
}
