package com.example.speedoTransfer.service;


import com.example.speedoTransfer.dto.AccountDTO;
import com.example.speedoTransfer.dto.CreateAccountDTO;
import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Set;

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
    AccountDTO getAccountById(Long accountId) throws AccountNotFoundException;

    /**
     * Get all transactions for an account
     * @param accountId
     * @return
     * @throws ResourceNotFoundException
     */
    Set<TransactionDTO> getAllTransactions(Long accountId) throws ResourceNotFoundException;


}
