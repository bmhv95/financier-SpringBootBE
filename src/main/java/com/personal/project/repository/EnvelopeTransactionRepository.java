package com.personal.project.repository;

import com.personal.project.entity.EnvelopeTransaction;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EnvelopeTransactionRepository extends TransactionRepository<EnvelopeTransaction> {
}
