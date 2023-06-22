package com.personal.project.repository;

import com.personal.project.entity.Account;
import com.personal.project.entity.Envelope;
import com.personal.project.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

public interface GoalRepository extends EnvelopeRepository<Goal> {
    @Override
    @Query("SELECT e FROM Goal e WHERE e.account.ID = ?1")
    List<Goal> getEnvelopesByAccountID(Long accountID);
}
