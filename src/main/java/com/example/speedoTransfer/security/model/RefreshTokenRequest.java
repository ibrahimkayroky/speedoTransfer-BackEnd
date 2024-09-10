package com.example.speedoTransfer.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest implements Serializable {
    private String refreshToken;
}
