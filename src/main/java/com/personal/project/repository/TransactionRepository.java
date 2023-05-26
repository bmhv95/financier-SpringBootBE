package com.personal.project.repository;

import com.personal.project.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TransactionRepository<T extends Transaction> extends JpaRepository<T, Long> {
}
