package com.example.speedoTransfer.dto;


import com.example.speedoTransfer.enumeration.AccountCurrency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO implements Serializable {

    private Long id;

    private String accountNumber;

    private Double balance;

    private String accountName;

    private AccountCurrency currency;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
