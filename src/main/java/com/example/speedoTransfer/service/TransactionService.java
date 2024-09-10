package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.dto.TransactionTransferDTO;
import com.example.speedoTransfer.dto.TransactionWithAccountDTO;
import com.example.speedoTransfer.enumeration.TransactionStatus;
import com.example.speedoTransfer.model.Account;
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
import java.util.Set;


@Service
@AllArgsConstructor
public class TransactionService implements ITransactionService{
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TransactionTransferDTO Transfer(TransactionTransferDTO transactionTransferDTO, Long userId) {
        Account senderAccount = accountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        User receiverAccountUser = userRepository.findByEmail(transactionTransferDTO.getReceiverAccount())
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

        Account receiverAccount = accountRepository.findById(receiverAccountUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

        if (senderAccount.getBalance() < transactionTransferDTO.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        Transaction transaction = Transaction.builder()
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .amount(transactionTransferDTO.getAmount())
                .status(TransactionStatus.SENT)
                .build();

        senderAccount.setBalance(senderAccount.getBalance() - transactionTransferDTO.getAmount());
        receiverAccount.setBalance(receiverAccount.getBalance() + transactionTransferDTO.getAmount());

        System.out.println("Transaction saved successfully with ID1: " + transaction.getId());

        // Add transaction to the account's transaction lists
//        senderAccount.getSentTransactions().add(transaction);
//
//        System.out.println("Transaction saved successfully with ID2: " + transaction.getId());
//        receiverAccount.getReceivedTransactions().add(transaction);

        // Save the updated accounts
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        Transaction savedTransaction = this.transactionRepository.save(transaction);

        System.out.println("Transaction saved successfully with ID3: " + savedTransaction.getId());

        return savedTransaction.toDTOTransfer();
    }




    ///////neeeeeeed to maintainance
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public TransactionWithAccountDTO getTransactionById(Long id) {
        TransactionDTO transactionDTO = transactionRepository.findById(id).orElseThrow(null).toDTO();
//        User senderAccount = userRepository.findByName(transactionDTO.getSenderAccount())
//                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        User receiverAccount = userRepository.findByName(transactionDTO.getReceiverName())
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

        Transaction transaction = Transaction.builder()
                .id(transactionDTO.getId())
//                .senderAccount(senderAccount.getAccount())
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
