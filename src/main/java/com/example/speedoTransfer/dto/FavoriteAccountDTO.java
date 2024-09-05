package com.example.speedoTransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteAccountDTO {
    private Long id;
    private String recipientAccount;
    private String recipientName;
}
