package com.example.speedoTransfer.service;


import com.example.speedoTransfer.dto.AccountDTO;
import com.example.speedoTransfer.dto.CreateAccountDTO;
import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.dto.UpdateUserDTO;
import com.example.speedoTransfer.enumeration.AccountCurrency;
import com.example.speedoTransfer.exception.custom.FavoriteRecipientException;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.Account;
import com.example.speedoTransfer.model.Transaction;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.AccountRepository;
import com.example.speedoTransfer.repository.TransactionRepository;
import com.example.speedoTransfer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public AccountDTO createAccount(CreateAccountDTO accountDTO) throws ResourceNotFoundException {

        User user = this.userRepository.findById(accountDTO.getUserId()).orElseThrow(()
                -> new ResourceNotFoundException("User id " + accountDTO.getUserId() + " not found"));

        Account account = Account.builder()
                .accountNumber(new SecureRandom().nextInt(1000000000) + "")
                .currency(AccountCurrency.USD)
                .balance((double) new SecureRandom().nextInt(1000000))
                .user(user)
                .build();

        Account savedAccount = this.accountRepository.save(account);

        return savedAccount.toDTO();
    }

    @Override
    public AccountDTO getAccountById(Long accountId) throws AccountNotFoundException {
        return this.accountRepository.findById(accountId).orElseThrow(()
                -> new AccountNotFoundException("Account not found")).toDTO();
    }

    @Override
    public Set<TransactionDTO> getAllTransactions(Long accountId) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        Set<Transaction> allTransactions = new HashSet<>(account.getSentTransactions());
        allTransactions.addAll(account.getReceivedTransactions());

        return allTransactions.stream()
                .map(Transaction::toDTO)
                .collect(Collectors.toSet());
    }


    @Override
    public void deposit(Long accountId, Double amount) {

    }
}
