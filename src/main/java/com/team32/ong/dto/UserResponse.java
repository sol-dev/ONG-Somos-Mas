package com.team32.ong.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private RoleDto role;
}
