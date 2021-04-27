package com.team32.ong.dto;

import com.team32.ong.model.Role;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
public class NewuserDto {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Role role;

}
