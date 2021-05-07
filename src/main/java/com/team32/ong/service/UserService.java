package com.team32.ong.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.team32.ong.dto.NewUserDto;
import com.team32.ong.dto.UserDto;
import com.team32.ong.dto.UserDtoRequestForUser;

import javassist.NotFoundException;

@Service
public interface UserService {

    UserDto save(UserDto user);

    UserDto findById(Long id) throws NotFoundException;
	
	NewUserDto updateAdminOnly(Optional<UserDto> userDtoFound, UserDto newUserDto) throws NotFoundException;

	NewUserDto updateForUser(Optional<UserDto> userDtoFound, UserDtoRequestForUser userDto) throws NotFoundException;

	void delete(Long id) throws NotFoundException;
}
