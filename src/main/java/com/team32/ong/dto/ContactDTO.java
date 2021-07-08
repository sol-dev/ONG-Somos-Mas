package com.team32.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    private String name;

    private String email;

    private String message;

    private Long phone;
}
