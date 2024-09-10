package com.example.speedoTransfer.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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
