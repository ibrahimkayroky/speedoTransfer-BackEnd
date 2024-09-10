package com.example.speedoTransfer.model;

import com.example.speedoTransfer.dto.FavoriteRecipientDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FavoriteRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Recipient account cannot be blank")
    @Column(nullable = false)
    private String recipientAccount;

    @NotBlank(message = "Recipient Name cannot be blank")
    @Column(nullable = false)
    private String recipientName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private User user;

    public FavoriteRecipientDTO toDTO() {
        return FavoriteRecipientDTO.builder()
                .id(this.id)
                .recipientAccount(this.recipientAccount)
                .recipientName(this.recipientName)
                .userId(user.getId())
                .build();
    }

}
