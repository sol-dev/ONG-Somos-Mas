package com.team32.ong.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class RoleDto {

    @NotEmpty
    private String name;
    private String description;
}