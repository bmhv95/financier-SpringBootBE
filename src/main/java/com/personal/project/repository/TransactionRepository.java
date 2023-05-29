package com.personal.project.repository;

import com.personal.project.entity.Account;
import com.personal.project.entity.Transaction;
import com.personal.project.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDate;
import java.util.List;

@NoRepositoryBean
public interface TransactionRepository<T extends Transaction> extends JpaRepository<T, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.wallet.account.email = ?1")
    List<T> findAllByEmail(String email);

    List<T> findAllByAccount(Account account);

    List<T> findAllByWallet(Wallet wallet);

    List<T> findAllBetweenMonths(String email, LocalDate startDate, LocalDate endDate);
}
