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

//    @NotBlank(message = "Receiver name is required")
    private String receiverName;

//    @NotBlank(message = "Receiver account is required")
//    @Email
    private String receiverAccount;
    private TransactionStatus status;
    private Double amount;

}
