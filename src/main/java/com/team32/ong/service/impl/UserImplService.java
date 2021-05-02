package com.team32.ong.service.impl;

import com.team32.ong.dto.UserDto;
import com.team32.ong.model.User;
import com.team32.ong.repository.UserRepository;
import com.team32.ong.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImplService implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDto save(UserDto user) {

        User userEntity = userRepo.save(dtoToEntity(user));

        return entityToDto(userEntity);
    }


    private User dtoToEntity(UserDto userDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userDto, User.class);
    }

    private UserDto entityToDto(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserDto.class);
    }
}
