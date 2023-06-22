package com.personal.project.repository;

import com.personal.project.entity.Spending;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpendingRepository extends EnvelopeRepository<Spending> {
    @Override
    @Query("SELECT e FROM Spending e WHERE e.account.ID = ?1")
    List<Spending> getEnvelopesByAccountID(Long accountID);
}
