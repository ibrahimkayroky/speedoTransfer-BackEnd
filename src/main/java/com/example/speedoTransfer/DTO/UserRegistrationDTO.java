package com.example.speedoTransfer.DTO;

import jakarta.validation.constraints.NotEmpty;

public class UserRegistrationDTO {

    @NotEmpty
    private String Email;

    @NotEmpty
    private String Password;

}
