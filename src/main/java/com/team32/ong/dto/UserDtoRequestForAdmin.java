package com.team32.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequestForAdmin {
	
	private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
    private RoleDto role;

}