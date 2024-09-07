package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.FavoriteRecipientDTO;
import com.example.speedoTransfer.dto.UserDTO;
import com.example.speedoTransfer.exception.custom.FavoriteRecipientNotFoundException;
import com.example.speedoTransfer.exception.custom.UserAlreadyExistsException;
import com.example.speedoTransfer.exception.custom.UserDoesNotExistsException;
import com.example.speedoTransfer.model.Account;
import com.example.speedoTransfer.model.FavoriteRecipient;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.AccountRepository;
import com.example.speedoTransfer.repository.FavoriteRecipientRepository;
import com.example.speedoTransfer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FavoriteRecipientService implements IFavoriteRecipientService {

    private final FavoriteRecipientRepository favoriteRecipientRepository;

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;
    @Override
    @Transactional
    public FavoriteRecipientDTO addFavoriteRecipient(FavoriteRecipientDTO favoriteRecipientDTO) throws UserDoesNotExistsException {

        User user = userRepository.findById(favoriteRecipientDTO.getUserId())
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
    public FavoriteRecipientDTO getFavoriteRecipientById(Long id) throws FavoriteRecipientNotFoundException {
        FavoriteRecipient favoriteRecipient = favoriteRecipientRepository.findById(id)
                .orElseThrow(() -> new FavoriteRecipientNotFoundException("FavoriteRecipient not found with id: " + id));

        return favoriteRecipient.toDTO();
    }

    @Override
    public List<FavoriteRecipient> getFavoriteRecipientsByUserId(Long userId) {
        return favoriteRecipientRepository.findByUserId(userId);
    }

    @Override
    public void deleteFavoriteRecipientById(Long id) {
        if (!favoriteRecipientRepository.existsById(id)) {
            throw new IllegalArgumentException("FavoriteRecipient not found");
        }
        favoriteRecipientRepository.deleteById(id);

    }
}
