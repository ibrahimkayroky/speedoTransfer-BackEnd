package com.example.speedoTransfer.repository;

import com.example.speedoTransfer.model.FavoriteRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRecipientRepository extends JpaRepository<FavoriteRecipient,Long> {
}
