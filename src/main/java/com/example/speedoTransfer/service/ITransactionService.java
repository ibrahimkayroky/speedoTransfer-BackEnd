package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.dto.TransactionWithAccountDTO;
import com.example.speedoTransfer.model.Transaction;

import java.util.List;

public interface ITransactionService
{
    TransactionDTO Transfer(TransactionDTO transactionDTO,Long userId);
    TransactionWithAccountDTO getTransactionById(Long id);
    List<Transaction> getTransactionsByUserId(Long accountId);
}
