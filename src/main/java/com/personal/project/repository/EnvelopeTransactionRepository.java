package com.personal.project.repository;

import com.personal.project.entity.EnvelopeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvelopeTransactionRepository extends JpaRepository<EnvelopeTransaction, Long> {
}
