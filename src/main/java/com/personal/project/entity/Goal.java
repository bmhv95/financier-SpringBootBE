package com.personal.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long goalID;

    private String goalName;

    @Column(nullable = false)
    @Min(value = 0, message = "Goal budget must not be negative")
    private BigDecimal goalAmount;
    private BigDecimal goalCurrentBalance;

    @CreationTimestamp
    private LocalDate goalStartDate;

    private LocalDate goalEndDate;

    @ManyToOne
    @JoinColumn(name = "acc_id")
    private Account account;

    @OneToMany(mappedBy = "goal")
    private List<GoalTransaction> goalTransaction;
}
