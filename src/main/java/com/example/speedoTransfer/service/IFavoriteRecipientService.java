package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.FavoriteRecipientDTO;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.FavoriteRecipient;
import java.util.List;

public interface IFavoriteRecipientService {
    FavoriteRecipientDTO addFavoriteRecipient(FavoriteRecipientDTO favoriteRecipientDTO);
    FavoriteRecipientDTO getFavoriteRecipientById(Long id) throws ResourceNotFoundException;

    List<FavoriteRecipient> getFavoriteRecipientsByAccountId(Integer accountId);
    void deleteFavoriteRecipientById(Long id);
}
