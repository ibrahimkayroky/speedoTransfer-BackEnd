package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.UpdateUserDTO;
import com.example.speedoTransfer.dto.UserDTO;
import com.example.speedoTransfer.exception.custom.FavoriteRecipientException;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.FavoriteRecipient;
import com.example.speedoTransfer.model.User;

import java.util.Set;

public interface IUserService {

    /**
     * Get customer by id
     *
     * @param userId the customer id
     * @return the created customer
     * @throws ResourceNotFoundException if the customer is not found
     */
    UserDTO getUserById(Long userId) throws  ResourceNotFoundException;
    UserDTO updateUser(Long accountId, UpdateUserDTO accountDTO);
    void updatePassword(Long userId, String oldPassword, String newPassword);
    Set<FavoriteRecipient> getAllFavoriteRecipients(Long userId) throws FavoriteRecipientException;


}
