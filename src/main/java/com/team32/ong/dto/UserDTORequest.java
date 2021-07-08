package com.team32.ong.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class UserDTORequest {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
}
