package com.personal.project.entity;

import com.personal.project.listener.IncomeListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(IncomeListener.class)
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_id")
    private Long incomeID;

    private String incomeName;
    @Column(nullable = false)
    @Min(value = 0)
    private BigDecimal incomeAmount;
    @CreationTimestamp
    private LocalDate incomeDate;
    private boolean recurring;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}
