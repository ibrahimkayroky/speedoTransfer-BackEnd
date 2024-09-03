package com.example.speedoTransfer.dto;

import com.example.speedoTransfer.model.Account;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDTO {
    private Long id;
    private Account senderAccount;
    private Account receiverAccount;
    private Double amount;
    private String status;
    private LocalDateTime createdAt;
}
