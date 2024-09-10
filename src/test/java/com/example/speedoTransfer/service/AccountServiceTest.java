package com.example.speedoTransfer.service;

import com.example.speedoTransfer.dto.AccountDTO;
import com.example.speedoTransfer.dto.CreateAccountDTO;
import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.enumeration.AccountCurrency;
import com.example.speedoTransfer.enumeration.AccountType;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.Account;
import com.example.speedoTransfer.model.Transaction;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.AccountRepository;
import com.example.speedoTransfer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.security.auth.login.AccountNotFoundException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() throws ResourceNotFoundException {

        Long userId = 1L;
        CreateAccountDTO createAccountDTO = new CreateAccountDTO();
        createAccountDTO.setUserId(userId);

        User user = User.builder().id(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Account account = Account.builder()
                .accountNumber(String.valueOf(new SecureRandom().nextInt(1000000000)))
                .accountName(user.getName())
                .currency(AccountCurrency.USD)
                .accountType(AccountType.SAVINGS)
                .balance((double) new SecureRandom().nextInt(1000000))
                .user(user)
                .build();

        user.setAccount(account);
        when(accountRepository.save(account)).thenReturn(account);
        AccountDTO result = accountService.createAccount(createAccountDTO);

        assertNotNull(result);
        assertEquals("123456789", result.getAccountNumber());
        assertEquals(account.getBalance(), result.getBalance());
        verify(accountRepository,times(1)).save(any(Account.class));

    }
    @Test
    void testCreateAccount_UserNotFound() {
        Long userId = 1L;
        CreateAccountDTO createAccountDTO = new CreateAccountDTO();
        createAccountDTO.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.createAccount(createAccountDTO);
        });

        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void testGetAccountById_Success() throws AccountNotFoundException {
        Long accountId = 1L;
        Account account = Account.builder()
                .id(accountId)
                .accountNumber("123456789")
                .accountName("John Doe")
                .currency(AccountCurrency.USD)
                .accountType(AccountType.SAVINGS)
                .balance(1000.0)
                .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        AccountDTO result = accountService.getAccountById(accountId);

        assertNotNull(result);
        assertEquals(accountId, result.getId());
        assertEquals("123456789", result.getAccountNumber());
        assertEquals(account.getBalance(), result.getBalance());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void testGetAccountById_AccountNotFound() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAccountById(accountId);
        });

        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void testGetAllTransactions_success() throws ResourceNotFoundException {
        Long accountId = 1L;
        Long accountId2 = 2L;

        Account account = Account.builder()
                .id(accountId)
                .accountNumber("123456789")
                .accountName("John Doe")
                .currency(AccountCurrency.USD)
                .accountType(AccountType.SAVINGS)
                .balance(1000.0)
                .build();
        Account account2 = Account.builder()
                .id(accountId2)
                .accountNumber("13456789")
                .accountName("Jhn Doe")
                .currency(AccountCurrency.USD)
                .accountType(AccountType.SAVINGS)
                .balance(10000.0)
                .build();
        Transaction transaction = Transaction.builder()
                .id(1L)
                .senderAccount(account)
                .receiverAccount(account2)
                .amount(100.0)
                .build();


        account.setSentTransactions(Collections.singleton(transaction));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Set<TransactionDTO> result = accountService.getAllTransactions(accountId);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(accountRepository, times(1)).findById(accountId);
    }
    @Test
    void testGetAllTransactions_AccountNotFound() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAllTransactions(accountId);
        });

        verify(accountRepository, times(1)).findById(accountId);
    }


}
