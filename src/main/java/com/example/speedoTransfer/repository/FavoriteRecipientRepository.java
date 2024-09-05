package com.example.speedoTransfer.repository;

import com.example.speedoTransfer.model.FavoriteRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRecipientRepository extends JpaRepository<FavoriteRecipient,Long> {
    List<FavoriteRecipient> findByAccountId(Integer accountId);

}
