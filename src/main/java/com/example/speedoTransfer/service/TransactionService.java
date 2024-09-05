package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.enumeration.TransactionStatus;
import com.example.speedoTransfer.model.Account;
import com.example.speedoTransfer.model.Transaction;
import com.example.speedoTransfer.repository.AccountRepository;
import com.example.speedoTransfer.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class TransactionService implements ITransactionService{
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Account senderAccount = accountRepository.findById(transactionDTO.getSenderAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        Account receiverAccount = accountRepository.findById(transactionDTO.getReceiverAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));


        if (senderAccount.getBalance() < transactionDTO.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds");
    }
        senderAccount.setBalance(senderAccount.getBalance() - transactionDTO.getAmount());
        receiverAccount.setBalance(receiverAccount.getBalance() + transactionDTO.getAmount());
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        Transaction transaction = Transaction.builder()
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .amount(transactionDTO.getAmount())
                .status(TransactionStatus.COMPLETED.toString())
                .build();

        Transaction savedTransaction = this.transactionRepository.save(transaction);
        return savedTransaction.toDTO();

    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(null);
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findBySenderAccountId(accountId);
    }
}
