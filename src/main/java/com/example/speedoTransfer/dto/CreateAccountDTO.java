package com.example.speedoTransfer.dto;

import com.example.speedoTransfer.enumeration.AccountCurrency;
import com.example.speedoTransfer.enumeration.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountDTO {

    @NotNull
    private AccountType accountType;

    @NotBlank
    private String accountName;

    @NotNull
    private AccountCurrency currency;

    @NotNull
    private Long userId;


}
