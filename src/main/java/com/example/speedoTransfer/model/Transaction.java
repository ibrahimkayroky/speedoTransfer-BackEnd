package com.example.speedoTransfer.model;

import com.example.speedoTransfer.dto.TransactionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
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
    private String status;  // "COMPLETED", "FAILED"

    @CreationTimestamp
    private LocalDateTime createdAt;

    public TransactionDTO toDTO() {
        return TransactionDTO.builder()
                .id(this.id)
                .senderAccountId(this.senderAccount.getId())
                .receiverAccountId(this.receiverAccount.getId())
                .amount(this.amount)
                .status(this.status)
                .createdAt(this.createdAt)
                .build();
    }

}
