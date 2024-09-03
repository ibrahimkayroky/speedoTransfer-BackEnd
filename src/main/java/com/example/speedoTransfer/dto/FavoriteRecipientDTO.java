package com.example.speedoTransfer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavoriteRecipientDTO {
    private Long id;
    private String recipientAccount;
    private String recipientName;
    private Long userId;
}
