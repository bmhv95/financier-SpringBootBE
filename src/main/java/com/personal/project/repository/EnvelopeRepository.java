package com.personal.project.repository;

import com.personal.project.entity.Envelope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvelopeRepository extends JpaRepository<Envelope, Long> {
    @Query("SELECT e FROM Envelope e WHERE e.account.accountID = ?1")
    List<Envelope> getEnvelopesByAccountID(Long accountID);
}
