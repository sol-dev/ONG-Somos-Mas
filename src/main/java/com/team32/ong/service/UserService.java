package com.team32.ong.service;

import org.springframework.stereotype.Service;

import com.team32.ong.dto.NewUserDto;
import com.team32.ong.dto.UserDto;
import com.team32.ong.dto.UserDtoRequestForUser;

import javassist.NotFoundException;

@Service
public interface UserService {

    UserDto save(UserDto user);

    UserDto findById(Long id) throws NotFoundException;
	
	NewUserDto updateAdminOnly(Long id, UserDto newUserDto) throws NotFoundException;

	NewUserDto updateForUser(Long id, UserDtoRequestForUser userDto) throws NotFoundException;

	String delete(Long id) throws NotFoundException;
}
