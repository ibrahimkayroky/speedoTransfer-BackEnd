package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.dto.TransactionWithAccountDTO;
import com.example.speedoTransfer.enumeration.TransactionStatus;
import com.example.speedoTransfer.model.Transaction;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.AccountRepository;
import com.example.speedoTransfer.repository.TransactionRepository;
import com.example.speedoTransfer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class TransactionService implements ITransactionService{
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TransactionDTO Transfer(TransactionDTO transactionDTO,Long userId) {
        User senderAccount = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        User receiverAccount = userRepository.findByName(transactionDTO.getReceiverName())
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));


        if (senderAccount.getAccount().getBalance() < transactionDTO.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds");
    }
        senderAccount.getAccount().setBalance(senderAccount.getAccount().getBalance() - transactionDTO.getAmount());
        receiverAccount.getAccount().setBalance(receiverAccount.getAccount().getBalance() + transactionDTO.getAmount());
        accountRepository.save(senderAccount.getAccount());
        accountRepository.save(receiverAccount.getAccount());

        Transaction transaction = Transaction.builder()
                .senderAccount(senderAccount.getAccount())
                .receiverAccount(receiverAccount.getAccount())
                .amount(transactionDTO.getAmount())
                .status(TransactionStatus.SENT)
                .build();

        senderAccount.getAccount().getSentTransactions().add(transaction);
        transaction.setStatus(TransactionStatus.RECEIVED);
        receiverAccount.getAccount().getReceivedTransactions().add(transaction);


        Transaction savedTransaction = this.transactionRepository.save(transaction);
        return savedTransaction.toDTO();

    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public TransactionWithAccountDTO getTransactionById(Long id) {
        TransactionDTO transactionDTO = transactionRepository.findById(id).orElseThrow(null).toDTO();
        User senderAccount = userRepository.findByName(transactionDTO.getSenderAccount())
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        User receiverAccount = userRepository.findByName(transactionDTO.getReceiverName())
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

        Transaction transaction = Transaction.builder()
                .id(transactionDTO.getId())
                .senderAccount(senderAccount.getAccount())
                .receiverAccount(receiverAccount.getAccount())
                .amount(transactionDTO.getAmount())
                .createdAt(LocalDateTime.now())
                .build();

        TransactionWithAccountDTO transactionWithAccountDTO = transaction.toDTOWithAccount();
        return transactionWithAccountDTO;

    }

    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findBySenderAccountId(userId);
    }
}
