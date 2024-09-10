package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.UpdateUserDTO;
import com.example.speedoTransfer.dto.UserDTO;
import com.example.speedoTransfer.exception.custom.FavoriteRecipientException;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.FavoriteRecipient;

import java.util.Set;

public interface IUserService {

    /**
     * Get customer by id
     * @param userId the customer id
     * @return the created customer
     * @throws ResourceNotFoundException if the customer is not found
     */
    UserDTO getUserById(Long userId) throws  ResourceNotFoundException;

    /**
     * Update user
     * @param email
     * @param accountDTO
     * @return
     */
    UserDTO updateUser(String email, UpdateUserDTO accountDTO);

    /**
     * Update password
     * @param email
     * @param oldPassword
     * @param newPassword
     */
    void updatePassword(String email, String oldPassword, String newPassword);

    /**
     * Get all favorite recipients
     * @param userId
     * @return
     * @throws FavoriteRecipientException
     */
    Set<FavoriteRecipient> getAllFavoriteRecipients(Long userId) throws FavoriteRecipientException;


}
