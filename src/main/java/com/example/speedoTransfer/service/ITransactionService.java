package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.dto.TransactionTransferDTO;
import com.example.speedoTransfer.dto.TransactionWithAccountDTO;
import com.example.speedoTransfer.model.Transaction;

import java.util.List;

public interface ITransactionService
{
    /**
     *
     * @param transactionDTO
     * @param userId
     * @return
     */
    TransactionTransferDTO Transfer(TransactionTransferDTO transactionDTO, Long userId);

    /**
     *
     * @param id
     * @return
     */
    TransactionWithAccountDTO getTransactionById(Long id);

    /**
     *
     * @param accountId
     * @return
     */
    List<Transaction> getTransactionsByUserId(Long accountId);
}
