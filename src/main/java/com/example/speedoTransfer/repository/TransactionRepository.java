package com.example.speedoTransfer.repository;

import com.example.speedoTransfer.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
