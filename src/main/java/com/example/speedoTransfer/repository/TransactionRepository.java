package com.example.speedoTransfer.repository;

import com.example.speedoTransfer.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findBySenderAccountId(Long id);
}
