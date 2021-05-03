package com.team32.ong.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.team32.ong.model.Role;

import lombok.Getter;

@Getter
public class NewuserDto {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
    @NotNull
    private Role role;

}
