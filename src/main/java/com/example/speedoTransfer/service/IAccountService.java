package com.example.speedoTransfer.service;


import com.example.speedoTransfer.dto.AccountDTO;
import com.example.speedoTransfer.dto.CreateAccountDTO;
import com.example.speedoTransfer.dto.UpdateUserDTO;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;

public interface IAccountService {

    /**
     * Create a new account
     *
     * @param accountDTO the account to be created
     * @return the created account
     * @throws ResourceNotFoundException if the account is not found
     */
    AccountDTO createAccount(CreateAccountDTO accountDTO) throws ResourceNotFoundException;

    /**
     * Get account by id
     *
     * @param accountId the account id
     * @return the account
     * @throws ResourceNotFoundException if the account is not found
     */
    AccountDTO getAccountById(Long accountId) throws ResourceNotFoundException;

    void deposit(Long accountId, Double amount);


}
