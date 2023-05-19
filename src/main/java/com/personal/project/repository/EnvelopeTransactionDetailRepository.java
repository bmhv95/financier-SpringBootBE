package com.personal.project.repository;

import com.personal.project.entity.EnvelopeTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvelopeTransactionDetailRepository extends JpaRepository<EnvelopeTransactionDetail, Long> {
}
