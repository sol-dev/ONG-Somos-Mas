package com.team32.ong.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NewUserDto {

	private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private RoleDto role;
	@Override
	public String toString() {
		return "NewUserDto [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", photo=" + photo
				+ ", role=" + role + "]";
	}
    
    
}
