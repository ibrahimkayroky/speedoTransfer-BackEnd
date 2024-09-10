package com.example.speedoTransfer.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse implements Serializable {
    private String token;
    private String refreshToken;

}
