package com.example.speedoTransfer.dto;


import com.example.speedoTransfer.enumeration.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDTO {

    private String name;


    private String email;

    private Country country;

    private Date birthDate;


}
