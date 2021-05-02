package com.team32.ong.service;

import com.team32.ong.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDto save(UserDto user);
}
