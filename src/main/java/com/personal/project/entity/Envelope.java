package com.personal.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @CreatedDate
    private LocalDate envelopeDate;

    @ManyToOne
    @JoinColumn(name = "acc_id")
    private Account account;
}