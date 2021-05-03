package com.team32.ong.dto;

import com.team32.ong.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
    private RoleDto role;
}
