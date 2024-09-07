package com.example.speedoTransfer.dto;


import com.example.speedoTransfer.model.Account;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Double balance;

    private Date birthDate;

}
