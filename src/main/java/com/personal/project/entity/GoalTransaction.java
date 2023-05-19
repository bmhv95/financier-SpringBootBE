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
public class GoalTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_transaction_id")
    private Long goalTransactionID;

    private LocalDate goalTransactionDate;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @OneToMany(mappedBy = "goalTransaction")
    private List<GoalTransactionDetail> goalTransactionDetails;
}
