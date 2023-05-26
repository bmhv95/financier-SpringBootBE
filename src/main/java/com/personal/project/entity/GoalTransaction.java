package com.personal.project.entity;

import com.personal.project.listener.TransactionListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("GOAL")
@EntityListeners(TransactionListener.class)
public class GoalTransaction extends Transaction {
    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @PostPersist
    public void updateGoal() {
        goal.setGoalCurrentBalance(goal.getGoalCurrentBalance().add(super.getTransactionAmount()));
    }
}
