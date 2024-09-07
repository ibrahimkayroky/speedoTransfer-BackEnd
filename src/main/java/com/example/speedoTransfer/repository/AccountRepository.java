package com.example.speedoTransfer.repository;

import com.example.speedoTransfer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findById(Long id);

    Optional<Account> findByAccountName(String accountName);

}
