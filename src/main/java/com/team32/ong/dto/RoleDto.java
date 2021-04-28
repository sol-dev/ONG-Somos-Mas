package com.team32.ong.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class RoleDto {

    @NotNull
    private String name;
    private String description;
}