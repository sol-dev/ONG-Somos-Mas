package com.team32.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {

    private Long id;

    private String name;

    private String image;

    private String address;

    private Integer phone;

    @Email
    private String email;

    private String welcomeText;

    private String aboutUsText;

}