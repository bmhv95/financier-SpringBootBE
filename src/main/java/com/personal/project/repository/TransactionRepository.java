package com.personal.project.repository;

import com.personal.project.entity.Account;
import com.personal.project.entity.Transaction;
import com.personal.project.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

@NoRepositoryBean
public interface TransactionRepository<T extends Transaction> extends JpaRepository<T, Long> {
    Collection<? extends Transaction> findAllByEmail(String email);

    Collection<? extends Transaction> findAllByAccount(Account account);

    Collection<? extends Transaction> findAllByWallet(Wallet wallet);
}
