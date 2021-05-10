package com.team32.ong.service;

import com.team32.ong.dto.UserDTORequest;
import com.team32.ong.dto.UserDTOResponse;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDTOResponse save(UserDTORequest user) throws NotFoundException;
    List<UserDTOResponse> getAllUsers();
}
