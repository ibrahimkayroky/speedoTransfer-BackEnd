package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.FavoriteRecipientDTO;
import com.example.speedoTransfer.exception.custom.FavoriteRecipientNotFoundException;
import com.example.speedoTransfer.exception.custom.UserDoesNotExistsException;
import com.example.speedoTransfer.model.FavoriteRecipient;
import java.util.List;

public interface IFavoriteRecipientService {
    FavoriteRecipientDTO addFavoriteRecipient(FavoriteRecipientDTO favoriteRecipientDTO) throws UserDoesNotExistsException;
    FavoriteRecipientDTO getFavoriteRecipientById(Long id) throws FavoriteRecipientNotFoundException;

    List<FavoriteRecipient> getFavoriteRecipientsByUserId(Long userId);
    void deleteFavoriteRecipientById(Long id);
}
