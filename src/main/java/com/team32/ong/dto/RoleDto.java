package com.team32.ong.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RoleDto {

    @NotEmpty
    private String name;
    private String description;
}