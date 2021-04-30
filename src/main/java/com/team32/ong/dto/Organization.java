package com.team32.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String image;

    private String address;

    private Integer phone;

    @NotEmpty
    @Email
    private String email;

    private String welcomeText;

    private String aboutUsText;

}