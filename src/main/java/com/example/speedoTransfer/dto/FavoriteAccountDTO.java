package com.example.speedoTransfer.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Recipient account cannot be blank")
    @Column(nullable = false)
    @Email(message = "Email should be valid")
    private String recipientAccount;

    @NotBlank(message = "Recipient name cannot be blank")
    @Column(nullable = false)
    private String recipientName;
}
