package com.personal.project.repository;

import com.personal.project.entity.Account;
import com.personal.project.entity.Envelope;
import com.personal.project.entity.Transaction;
import com.personal.project.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByWallet(Wallet wallet);

    List<Transaction> findAllByEnvelope(Envelope envelope);

    @Query("SELECT t FROM Transaction t WHERE t.wallet.account.ID = :accountID")
    List<Transaction> findAllByAccountID(Long accountID);

    @Query("SELECT t FROM Transaction t WHERE t.date BETWEEN :startDate AND :endDate and t.wallet.account.email = :email")
    List<Transaction> getAllTransactionsBetweenMonths(@Param("email") String email, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
