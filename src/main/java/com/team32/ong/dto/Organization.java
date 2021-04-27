package com.team32.ong.dto;

import com.team32.ong.model.OrganizationEntity;
import lombok.Data;

@Data
public class Organization {

    private Long id;

    private String name;

    private String image;

    private String address;

    private Integer phone;

    private String email;

    private String welcomeText;

    private String aboutUsText;



}