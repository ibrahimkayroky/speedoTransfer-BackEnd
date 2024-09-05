package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.model.Transaction;

import java.util.List;

public interface ITransactionService
{
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    Transaction getTransactionById(Long id);
    List<Transaction> getTransactionsByAccountId(Long accountId);
}
