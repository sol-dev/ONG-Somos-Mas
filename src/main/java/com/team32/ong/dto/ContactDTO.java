package com.team32.ong.dto;

import lombok.Data;

@Data
public class ContactDTO {
    private String name;

    private String email;

    private String message;

    private Long phone;
}
