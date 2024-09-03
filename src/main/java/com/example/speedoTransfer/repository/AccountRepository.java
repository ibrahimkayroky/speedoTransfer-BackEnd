package com.example.speedoTransfer.repository;

import com.example.speedoTransfer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
