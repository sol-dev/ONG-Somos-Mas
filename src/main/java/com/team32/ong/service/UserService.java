package com.team32.ong.service;

import com.team32.ong.dto.UserDto;
import com.team32.ong.model.User;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDto save(UserDto user);
    public UserDto getOne(Long id);
    
    public User dtoToEntity(UserDto userDto);
    public UserDto entityToDto(User user);
    
}
