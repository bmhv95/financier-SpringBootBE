package com.personal.project.repository;

import com.personal.project.entity.Envelope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@NoRepositoryBean
public interface EnvelopeRepository<T extends Envelope> extends JpaRepository<Envelope, Long> {
    @Query("SELECT e FROM Envelope e WHERE e.account.ID = ?1")
    List<T> getEnvelopesByAccountID(Long accountID);

    @Query("SELECT e FROM Envelope e WHERE e.account.email = ?1")
    List<T> getEnvelopesByEmail(String email);

    @Query("SELECT e FROM Envelope e WHERE e.ID = ?1")
    T getEnvelopeByID(Long envelopeID);
}
