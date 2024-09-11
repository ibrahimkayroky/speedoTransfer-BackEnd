package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.FavoriteRecipientDTO;
import com.example.speedoTransfer.exception.custom.FavoriteRecipientNotFoundException;
import com.example.speedoTransfer.model.FavoriteRecipient;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.FavoriteRecipientRepository;
import com.example.speedoTransfer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "recipient")
public class FavoriteRecipientService implements IFavoriteRecipientService {

    private final FavoriteRecipientRepository favoriteRecipientRepository;


    private final UserRepository userRepository;
    @Override
    @Transactional

    public FavoriteRecipientDTO addFavoriteRecipient(FavoriteRecipientDTO favoriteRecipientDTO){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));


        FavoriteRecipient favoriteRecipient = FavoriteRecipient.builder()
                .recipientName(favoriteRecipientDTO.getRecipientName())
                .recipientAccount(favoriteRecipientDTO.getRecipientAccount())
                .user(user)
                .build();
        FavoriteRecipient savedFavoriteRecipient = this.favoriteRecipientRepository.save(favoriteRecipient);
        user.getFavoriteRecipients().add(favoriteRecipient);
        return savedFavoriteRecipient.toDTO();
    }

    @Override
    @Cacheable(cacheNames = "recipient")
    public FavoriteRecipientDTO getFavoriteRecipientById(Long id) throws FavoriteRecipientNotFoundException {
        FavoriteRecipient favoriteRecipient = favoriteRecipientRepository.findById(id)
                .orElseThrow(() -> new FavoriteRecipientNotFoundException("FavoriteRecipient not found with id: " + id));

        return favoriteRecipient.toDTO();
    }

    @Override
//    @Cacheable(cacheNames = "recipientList")
    public List<FavoriteRecipient> getFavoriteRecipientsByUserId(Long userId) {
        return favoriteRecipientRepository.findByUserId(userId);
    }

    @Override
    @CacheEvict(cacheNames = "recipient")
    public void deleteFavoriteRecipientById(Long id) {
        if (!favoriteRecipientRepository.existsById(id)) {
            throw new IllegalArgumentException("FavoriteRecipient not found");
        }
        favoriteRecipientRepository.deleteById(id);

    }

    @Override
    @CacheEvict(cacheNames = "recipient")
    public void deleteFavoriteRecipientByUserIdAndDetails(Long id, String recipientAccount) throws FavoriteRecipientNotFoundException {
        Optional<FavoriteRecipient> favoriteRecipientOpt = favoriteRecipientRepository
                .findByUserIdAndRecipientAccount(id, recipientAccount);

        if (favoriteRecipientOpt.isEmpty()) {
            throw new FavoriteRecipientNotFoundException("Favorite recipient not found for user with ID: " + id);
        }

        FavoriteRecipient favoriteRecipient = favoriteRecipientOpt.get();
        favoriteRecipientRepository.delete(favoriteRecipient);
    }
}
