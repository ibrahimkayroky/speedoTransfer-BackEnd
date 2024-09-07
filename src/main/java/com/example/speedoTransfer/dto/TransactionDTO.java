package com.example.speedoTransfer.dto;

import com.example.speedoTransfer.enumeration.TransactionStatus;
import com.example.speedoTransfer.model.Account;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDTO {
    private Long id;
    private String senderAccount;
    private String receiverName;
    private String receiverAccount;
    private TransactionStatus status;
    private Double amount;
}
