package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.FavoriteRecipientDTO;
import com.example.speedoTransfer.exception.custom.FavoriteRecipientNotFoundException;
import com.example.speedoTransfer.exception.custom.UserDoesNotExistsException;
import com.example.speedoTransfer.model.FavoriteRecipient;
import java.util.List;

public interface IFavoriteRecipientService {

    /**
     * Adds a new favorite recipient for a user.
     * @param favoriteRecipientDTO
     * @return FavoriteRecipientDTO
     * @throws UserDoesNotExistsException
     */
    FavoriteRecipientDTO addFavoriteRecipient(FavoriteRecipientDTO favoriteRecipientDTO) throws UserDoesNotExistsException;

    /**
     *
     * @param id
     * @return FavoriteRecipientDTO
     * @throws FavoriteRecipientNotFoundException
     */
    FavoriteRecipientDTO getFavoriteRecipientById(Long id) throws FavoriteRecipientNotFoundException;

    /**
     *
     * @param userId
     * @return List<FavoriteRecipient>
     */
    List<FavoriteRecipient> getFavoriteRecipientsByUserId(Long userId);

    /**
     *
     * @param id
     */
    void deleteFavoriteRecipientById(Long id);

    /**
    *
    * @param id
    * @param accountNumber
    * @throws FavoriteRecipientNotFoundException
    */
    void deleteFavoriteRecipientByUserIdAndDetails(Long id, String accountNumber) throws FavoriteRecipientNotFoundException;
}
