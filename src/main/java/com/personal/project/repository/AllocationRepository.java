package com.personal.project.repository;

import com.personal.project.entity.Allocation;
import com.personal.project.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    @Query("SELECT e FROM Allocation e WHERE e.wallet.account.ID = ?1")
    List<Allocation> getAllocationsByAccountID(Long accountID);

    List<Allocation> findByWallet(Wallet wallet);
}
