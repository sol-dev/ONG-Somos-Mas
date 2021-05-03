package com.team32.ong.dto;

import lombok.Getter;

@Getter
public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
    private Role role;
}
