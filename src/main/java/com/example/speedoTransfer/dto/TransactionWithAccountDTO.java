package com.example.speedoTransfer.dto;

import com.example.speedoTransfer.enumeration.TransactionStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder

public class TransactionWithAccountDTO {

    private Long id;
    private String senderAccountName;
    private String receiverAccountName;
    private Double amount;
    private TransactionStatus status;
    private LocalDateTime createdAt;
}
