package com.example.speedoTransfer.model;

import com.example.speedoTransfer.dto.FavoriteRecipientDTO;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String recipientAccount;

    @Column(nullable = false)
    private String recipientName;

    public FavoriteRecipientDTO toDTO() {
        return FavoriteRecipientDTO.builder()
                .id(this.id)
                .recipientAccount(this.recipientAccount)
                .recipientName(this.recipientName)
                .build();
    }
}
