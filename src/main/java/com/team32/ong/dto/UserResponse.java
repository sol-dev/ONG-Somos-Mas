package com.team32.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
}
