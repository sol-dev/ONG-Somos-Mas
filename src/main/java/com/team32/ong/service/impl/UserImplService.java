package com.team32.ong.service.impl;

import java.util.Optional;

import com.team32.ong.dto.NewUserDto;
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

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public UserDto save(UserDto user) {

        user.setPassword(encoder.encode(user.getPassword()));

        Role role = roleRepo.findByName("USER");
        user.setRole(roleEntityToDto(role));

        User userEntity = userRepo.save(dtoToEntity(user));

        return entityToDto(userEntity);
    }


    private User dtoToEntity(UserDto userDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userDto, User.class);
    }
    
    private User newDtoToEntity(NewUserDto newUserDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(newUserDto, User.class);
    }

    private UserDto entityToDto(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserDto.class);
    }
    
    private NewUserDto entityToNewDto(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, NewUserDto.class);
    }

    private RoleDto roleEntityToDto(Role role){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(role, RoleDto.class);
    }

	@Override
	public UserDto findById(Long id) {
		Optional<User> userFound = userRepo.findById(id);
		return entityToDto(userFound.get());
	}
	
	@Override
	public NewUserDto update(UserDto userDtoFound, NewUserDto newUserDto) {
		Role roleEntity = roleRepo.findByName(newUserDto.getRole().getName());
		User userEntity = newDtoToEntity(newUserDto);
		userEntity.setId(userDtoFound.getId());
		userEntity.setRole(roleEntity);
		userEntity.setPassword(userDtoFound.getPassword());
		userRepo.save(userEntity);
		return entityToNewDto(userEntity);
	}
}
