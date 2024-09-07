package com.example.speedoTransfer.model;

import com.example.speedoTransfer.dto.TransactionDTO;
import com.example.speedoTransfer.dto.TransactionWithAccountDTO;
import com.example.speedoTransfer.enumeration.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_id", nullable = false)
    private Account senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_account_id", nullable = false)
    private Account receiverAccount;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private TransactionStatus status;  // "COMPLETED", "FAILED"

    @CreationTimestamp
    private LocalDateTime createdAt;

    public TransactionDTO toDTO() {
        return TransactionDTO.builder()
                .id(this.id)
                .senderAccount(this.senderAccount.getAccountName())
                .receiverAccount(this.receiverAccount.getUser().getEmail())
                .receiverName(this.receiverAccount.getUser().getName())
                .status(this.status)
                .amount(this.amount)
                .build();
    }

    public TransactionWithAccountDTO toDTOWithAccount() {
        return TransactionWithAccountDTO.builder()
                .id(this.id)
                .senderAccountName(this.senderAccount.getAccountName())
                .receiverAccountName(this.receiverAccount.getAccountName())
//                .status(TransactionStatus.COMPLETED)
                .amount(this.amount)
                .createdAt(this.createdAt)
                .build();
    }
}
