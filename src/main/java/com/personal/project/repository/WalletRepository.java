package com.personal.project.repository;

import com.personal.project.entity.Account;
import com.personal.project.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("SELECT w FROM Wallet w WHERE w.account.accountID = ?1")
    List<Wallet> getWalletsByAccountID(Long accountID);
}
