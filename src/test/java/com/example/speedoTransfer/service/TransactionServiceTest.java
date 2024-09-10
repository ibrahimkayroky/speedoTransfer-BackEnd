package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.TransactionTransferDTO;
import com.example.speedoTransfer.enumeration.TransactionStatus;
import com.example.speedoTransfer.model.Account;
import com.example.speedoTransfer.model.Transaction;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.AccountRepository;
import com.example.speedoTransfer.repository.TransactionRepository;
import com.example.speedoTransfer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testTransfer_Success() {
        MockitoAnnotations.openMocks(this);

        Long senderUserId = 1L;
        String receiverEmail = "receiver@example.com";
        double amount = 100.0;


        User senderUser = new User();
        senderUser.setId(senderUserId);
        senderUser.setName("sender");
        senderUser.setEmail("sender@example.com");

        Account senderAccount = new Account();
        senderAccount.setId(senderUserId);
        senderAccount.setBalance(200.0);
        senderAccount.setUser(senderUser);


        senderUser.setAccount(senderAccount);

        User receiverUser = new User();
        receiverUser.setName("asas");
        receiverUser.setId(2L);
        receiverUser.setEmail(receiverEmail);

        Account receiverAccount = new Account();
        receiverAccount.setId(2L);
        receiverAccount.setBalance(300.0);
        receiverAccount.setUser(receiverUser);

        receiverUser.setAccount(receiverAccount);

        TransactionTransferDTO transactionTransferDTO = new TransactionTransferDTO();
        transactionTransferDTO.setReceiverAccount(receiverEmail);
        transactionTransferDTO.setAmount(amount);

        when(accountRepository.findById(senderUserId)).thenReturn(Optional.of(senderAccount));
        when(userRepository.findByEmail(receiverEmail)).thenReturn(Optional.of(receiverUser));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(receiverAccount));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionTransferDTO result = transactionService.Transfer(transactionTransferDTO, senderUserId);

        assertNotNull(result);
        assertEquals(amount, result.getAmount());
        assertEquals(TransactionStatus.SENT, result.getStatus());
        verify(accountRepository, times(2)).save(any(Account.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testTransfer_InsufficientFunds() {
        MockitoAnnotations.openMocks(this);

        Long userId = 1L;
        String receiverEmail = "receiver@example.com";
        double amount = 1000.0;


        Account senderAccount = new Account();
        senderAccount.setId(userId);
        senderAccount.setBalance(500.0);

        User receiverUser = new User();
        receiverUser.setId(2L);
        receiverUser.setEmail(receiverEmail);

        Account receiverAccount = new Account();
        receiverAccount.setId(2L);
        receiverAccount.setBalance(300.0);

        TransactionTransferDTO transactionTransferDTO = new TransactionTransferDTO();
        transactionTransferDTO.setReceiverAccount(receiverEmail);
        transactionTransferDTO.setAmount(amount);

        when(accountRepository.findById(userId)).thenReturn(Optional.of(senderAccount));
        when(userRepository.findByEmail(receiverEmail)).thenReturn(Optional.of(receiverUser));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(receiverAccount));

        assertThrows(IllegalArgumentException.class, () -> {
            transactionService.Transfer(transactionTransferDTO, userId);
        });

        verify(accountRepository, times(0)).save(any(Account.class));
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    void testTransfer_SenderAccountNotFound() {
        MockitoAnnotations.openMocks(this);

        Long userId = 1L;
        TransactionTransferDTO transactionTransferDTO = new TransactionTransferDTO();
        transactionTransferDTO.setReceiverAccount("receiver@example.com");
        transactionTransferDTO.setAmount(100.0);

        when(accountRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            transactionService.Transfer(transactionTransferDTO, userId);
        });

        verify(accountRepository, times(0)).save(any(Account.class));
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    void testTransfer_ReceiverAccountNotFound() {
        MockitoAnnotations.openMocks(this);

        Long userId = 1L;
        TransactionTransferDTO transactionTransferDTO = new TransactionTransferDTO();
        transactionTransferDTO.setReceiverAccount("receiver@example.com");
        transactionTransferDTO.setAmount(100.0);

        Account senderAccount = new Account();
        senderAccount.setId(userId);
        senderAccount.setBalance(200.0);

        when(accountRepository.findById(userId)).thenReturn(Optional.of(senderAccount));
        when(userRepository.findByEmail("receiver@example.com")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            transactionService.Transfer(transactionTransferDTO, userId);
        });

        verify(accountRepository, times(0)).save(any(Account.class));
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    void testTransfer_ReceiverAccountFetchFailure() {
        MockitoAnnotations.openMocks(this);

        Long userId = 1L;
        String receiverEmail = "receiver@example.com";
        double amount = 100.0;

        Account senderAccount = new Account();
        senderAccount.setId(userId);
        senderAccount.setBalance(200.0);

        when(accountRepository.findById(userId)).thenReturn(Optional.of(senderAccount));
        when(userRepository.findByEmail(receiverEmail)).thenThrow(new RuntimeException("Database error"));

        TransactionTransferDTO transactionTransferDTO = new TransactionTransferDTO();
        transactionTransferDTO.setReceiverAccount(receiverEmail);
        transactionTransferDTO.setAmount(amount);

        assertThrows(RuntimeException.class, () -> {
            transactionService.Transfer(transactionTransferDTO, userId);
        });

        verify(accountRepository, times(0)).save(any(Account.class));
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }
}
