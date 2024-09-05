package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.FavoriteRecipientDTO;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.Account;
import com.example.speedoTransfer.model.FavoriteRecipient;
import com.example.speedoTransfer.repository.AccountRepository;
import com.example.speedoTransfer.repository.FavoriteRecipientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FavoriteRecipientService implements IFavoriteRecipientService {

    private final FavoriteRecipientRepository favoriteRecipientRepository;

    private final AccountRepository accountRepository;
    @Override
    public FavoriteRecipientDTO addFavoriteRecipient(FavoriteRecipientDTO favoriteRecipientDTO) {

        Account account = accountRepository.findById(favoriteRecipientDTO.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        FavoriteRecipient favoriteRecipient = FavoriteRecipient.builder()
                .recipientName(favoriteRecipientDTO.getRecipientName())
                .recipientAccount(favoriteRecipientDTO.getRecipientAccount())
                .account(account)
                .build();
        FavoriteRecipient savedFavoriteRecipient = this.favoriteRecipientRepository.save(favoriteRecipient);
        return savedFavoriteRecipient.toDTO();
    }

    @Override
    public FavoriteRecipientDTO getFavoriteRecipientById(Long id) throws ResourceNotFoundException {
        FavoriteRecipient favoriteRecipient = favoriteRecipientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FavoriteRecipient not found with id: " + id));

        return favoriteRecipient.toDTO();
    }

    @Override
    public List<FavoriteRecipient> getFavoriteRecipientsByAccountId(Integer accountId) {
        return favoriteRecipientRepository.findByAccountId(accountId);
    }

    @Override
    public void deleteFavoriteRecipientById(Long id) {
        if (!favoriteRecipientRepository.existsById(id)) {
            throw new IllegalArgumentException("FavoriteRecipient not found");
        }
        favoriteRecipientRepository.deleteById(id);

    }
}
