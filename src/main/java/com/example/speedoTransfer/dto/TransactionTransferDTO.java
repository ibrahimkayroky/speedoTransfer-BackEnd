package com.example.speedoTransfer.dto;

import com.example.speedoTransfer.enumeration.TransactionStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTransferDTO {
    private Long id;

    private String receiverName;
    private String receiverAccount;
    private TransactionStatus status;
    private Double amount;

}
