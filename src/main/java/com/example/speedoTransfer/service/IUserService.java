package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.UpdateUserDTO;
import com.example.speedoTransfer.dto.UserDTO;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;

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
    void changePassword(Long userId, String oldPassword, String newPassword);


}
