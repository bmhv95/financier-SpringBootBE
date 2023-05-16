package com.personal.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long goalID;

    private String goalName;
    private BigDecimal goalAmount;
    private BigDecimal goalCurrentBalance;
    private LocalDate goalStartDate;
    private LocalDate goalEndDate;

    @ManyToOne
    @JoinColumn(name = "acc_id")
    private Account account;
}
