package com.example.speedoTransfer.dto;

import com.example.speedoTransfer.enumeration.AccountCurrency;
import com.example.speedoTransfer.enumeration.AccountType;
import com.example.speedoTransfer.model.User;
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

    @NotNull
    private AccountCurrency currency;

    @NotBlank
    private String accountName;

    @NotNull
    private Long userId;


}
