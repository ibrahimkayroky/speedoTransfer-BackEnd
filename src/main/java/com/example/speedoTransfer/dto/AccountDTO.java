package com.example.speedoTransfer.dto;


import com.example.speedoTransfer.enumeration.AccountCurrency;
import com.example.speedoTransfer.enumeration.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {

    private Long id;

    private String accountNumber;

    private AccountType accountType;

    private Double balance;

    private AccountCurrency currency;

    private String accountName;

    private String accountDescription;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}