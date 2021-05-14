package com.team32.ong.service;

import org.springframework.stereotype.Service;

import com.team32.ong.dto.NewUserDto;
import com.team32.ong.dto.UserDTORequest;
import com.team32.ong.dto.UserDTOResponse;
import com.team32.ong.dto.UserDtoRequestForAdmin;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.model.User;

import javassist.NotFoundException;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {

    UserDTOResponse save(UserDTORequest user) throws NotFoundException, BadRequestException, IOException;
    List<UserDTOResponse> getAllUsers();
	UserDTOResponse getOne(Long id);
	UserDTOResponse findById(Long id) throws NotFoundException;
    User dtoToEntity(UserDTORequest userDTORequest);
    UserDTOResponse entityToDto(User user);
    NewUserDto updateAdminOnly(Long id, UserDtoRequestForAdmin newUserDto) throws NotFoundException;
	NewUserDto updateForUser(Long id, UserDTORequest userDto) throws NotFoundException;
	String delete(Long id) throws NotFoundException;
}
