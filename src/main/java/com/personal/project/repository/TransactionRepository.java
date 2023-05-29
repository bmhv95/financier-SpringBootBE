package com.personal.project.repository;

import com.personal.project.entity.Account;
import com.personal.project.entity.Transaction;
import com.personal.project.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.List;

@NoRepositoryBean
public interface TransactionRepository<T extends Transaction> extends JpaRepository<T, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.wallet.account.email = ?1")
    List<? extends Transaction> findAllByEmail(String email);

    List<? extends Transaction> findAllByAccount(Account account);

    List<? extends Transaction> findAllByWallet(Wallet wallet);
}
