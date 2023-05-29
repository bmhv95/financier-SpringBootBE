package com.personal.project.repository;

import com.personal.project.entity.Account;
import com.personal.project.entity.EnvelopeTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
public interface EnvelopeTransactionRepository extends TransactionRepository<EnvelopeTransaction> {
    @Override
    @Query("SELECT t FROM EnvelopeTransaction t WHERE t.wallet.account = ?1")
    List<EnvelopeTransaction> findAllByAccount(Account account);

    @Override
    @Query("SELECT t FROM EnvelopeTransaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
            "AND t.wallet.account.email = :email")
    List<EnvelopeTransaction> findAllBetweenMonths(String email, LocalDate startDate, LocalDate endDate);

    boolean existsById(Long transactionID);
}
