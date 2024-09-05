package com.example.speedoTransfer.model;

import com.example.speedoTransfer.dto.FavoriteAccountDTO;
import com.example.speedoTransfer.dto.FavoriteRecipientDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FavoriteRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recipientAccount;

    @Column(nullable = false)
    private String recipientName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public FavoriteRecipientDTO toDTO() {
        return FavoriteRecipientDTO.builder()
                .id(this.id)
                .recipientAccount(this.recipientAccount)
                .recipientName(this.recipientName)
                .accountId(account.getId())
                .build();
    }
    public FavoriteAccountDTO toDTO2()
    {
        return FavoriteAccountDTO.builder()
                .id(this.id)
                .recipientAccount(this.recipientAccount)
                .recipientName(this.recipientName)
                .build();
    }
}
