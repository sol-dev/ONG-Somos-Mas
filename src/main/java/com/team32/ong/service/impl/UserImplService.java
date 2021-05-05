package com.team32.ong.service.impl;

import com.team32.ong.dto.RoleDto;
import com.team32.ong.dto.UserDto;
import com.team32.ong.model.Role;
import com.team32.ong.model.User;
import com.team32.ong.repository.RoleRepository;
import com.team32.ong.repository.UserRepository;
import com.team32.ong.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserImplService implements UserService {

    @Autowired
    private UserRepository userRepo;

    //@Autowired
    //private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public UserDto save(UserDto user) {

        //user.setPassword(encoder.encode(user.getPassword()));

        Role role = roleRepo.findByName("USER");
        user.setRole(roleEntityToDto(role));

        User userEntity = userRepo.save(dtoToEntity(user));

        return entityToDto(userEntity);
    }
    
    @Override
    public UserDto getOne(Long id) {
    	User user = userRepo.getOne(id);
		return entityToDto(user);
    }
    

    public User dtoToEntity(UserDto userDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userDto, User.class);
    }

    public UserDto entityToDto(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserDto.class);
    }

    private RoleDto roleEntityToDto(Role role){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(role, RoleDto.class);
    }
}
