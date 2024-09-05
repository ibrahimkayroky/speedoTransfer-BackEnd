package com.example.speedoTransfer.service;


import com.example.speedoTransfer.dto.AccountDTO;
import com.example.speedoTransfer.dto.CreateAccountDTO;
import com.example.speedoTransfer.dto.UpdateUserDTO;
import com.example.speedoTransfer.exception.custom.ResourceNotFoundException;
import com.example.speedoTransfer.model.Account;
import com.example.speedoTransfer.model.User;
import com.example.speedoTransfer.repository.AccountRepository;
import com.example.speedoTransfer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public AccountDTO createAccount(CreateAccountDTO accountDTO) throws ResourceNotFoundException {

        User user = this.userRepository.findById(accountDTO.getUserId()).orElseThrow(()
                -> new ResourceNotFoundException("User id " + accountDTO.getUserId() + " not found"));

        Account account = Account.builder()
                .accountNumber(new SecureRandom().nextInt(1000000000) + "")
                .accountType(accountDTO.getAccountType())
                .accountName(accountDTO.getAccountName())
                .currency(accountDTO.getCurrency())
                .balance(0.0)
                .user(user)
                .build();

        Account savedAccount = this.accountRepository.save(account);

        return savedAccount.toDTO();
    }

    @Override
    public AccountDTO getAccountById(Long accountId) throws ResourceNotFoundException {
        return this.accountRepository.findById(accountId).orElseThrow(()
                -> new ResourceNotFoundException("Account not found")).toDTO();
    }




    @Override
    public void deposit(Long accountId, Double amount) {

    }
}
