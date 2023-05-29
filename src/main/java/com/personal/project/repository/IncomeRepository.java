package com.personal.project.repository;

import com.personal.project.entity.Income;
import com.personal.project.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    @Query("SELECT i FROM Income i WHERE i.wallet.account.accountID = ?1")
    List<Income> getIncomesByAccountID(Long accountID);

    List<Income> findByWallet(Wallet wallet);
}
