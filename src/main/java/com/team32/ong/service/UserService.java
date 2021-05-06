package com.team32.ong.service;

import com.team32.ong.model.User;
import com.team32.ong.dto.UserRequest;
import com.team32.ong.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    
    UserResponse save(UserRequest user);
	UserResponse getOne(Long id);
	UserResponse findById(Long id);

    public User dtoToEntity(UserResponse userResponse);
    public UserResponse entityToDto(User user);
}
