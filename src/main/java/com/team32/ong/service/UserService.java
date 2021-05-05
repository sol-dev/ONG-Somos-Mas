package com.team32.ong.service;

import com.team32.ong.dto.UserDto;
import org.springframework.stereotype.Service;

import com.team32.ong.dto.NewUserDto;

@Service
public interface UserService {

    UserDto save(UserDto user);

	UserDto findById(Long id);
	
	NewUserDto update(UserDto userDtoFound, NewUserDto newUserDto);
}
