package com.example.speedoTransfer.repository;

import com.example.speedoTransfer.model.FavoriteRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRecipientRepository extends JpaRepository<FavoriteRecipient,Long> {
    List<FavoriteRecipient> findByUserId(Long userId);
    Optional<FavoriteRecipient> findByUserIdAndRecipientAccount(Long userId, String recipientAccount);


}
