package com.example.speedoTransfer.dto;

import com.example.speedoTransfer.enumeration.TransactionStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TransactionDTO {
    private Long id;
    private String senderAccount;

    @NotBlank(message = "Sender name is required")
    private String receiverName;

    @NotBlank(message = "Receiver account is required")
    @Email
    private String receiverAccount;

    private TransactionStatus status;
    private Double amount;
}
