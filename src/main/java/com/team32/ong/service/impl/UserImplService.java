package com.team32.ong.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.team32.ong.dto.NewUserDto;
import com.team32.ong.dto.RoleDto;
import com.team32.ong.dto.UserDto;
import com.team32.ong.dto.UserDtoRequestForUser;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.model.Role;
import com.team32.ong.model.User;
import com.team32.ong.repository.RoleRepository;
import com.team32.ong.repository.UserRepository;
import com.team32.ong.service.UserService;

import javassist.NotFoundException;

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
    
    private User UserDtoRequestForUserToEntity(UserDtoRequestForUser userDto) {
    	ModelMapper mapper = new ModelMapper();
        return mapper.map(userDto, User.class);
	}

	@Override
	public UserDto findById(Long id) throws NotFoundException {
		Optional<User> userFound = userRepo.findById(id);
		if (!userFound.isPresent()){
            throw new NotFoundException("The user with id " + id + " does not exist");
        }
		return entityToDto(userFound.get());
	}
	
	@Override
	public NewUserDto updateAdminOnly(Optional<UserDto> userDtoFound, UserDto userDto) {
		StringBuffer errorsFound = new StringBuffer();
		if(userDto.getFirstName().isEmpty()) {
			errorsFound.append("El nombre no puede estar vacio. ");
		}
		if(userDto.getLastName().isEmpty()) {
			errorsFound.append("El apellido no puede estar vacio. ");
		}
		if(userDto.getEmail().isEmpty()) {
			errorsFound.append("El email no puede estar vacio. ");
		}
		if(userDto.getPassword().isEmpty()) {
			errorsFound.append("La contraseña no puede estar vacia.");
		}
		if(errorsFound.length() > 0) {
			throw new BadRequestException(errorsFound.toString());
		}
		Role roleEntity = roleRepo.findByName(userDto.getRole().getName());
		User userEntity = dtoToEntity(userDto);
		userEntity.setId(userDtoFound.get().getId());
		userEntity.setRole(roleEntity);
		userRepo.save(userEntity);
		return entityToNewDto(userEntity);
	}
	
	@Override
	public NewUserDto updateForUser(Optional<UserDto> userDtoFound, UserDtoRequestForUser userDto) {
		StringBuffer errorsFound = new StringBuffer();
		if(userDto.getFirstName().isEmpty()) {
			errorsFound.append("El nombre no puede estar vacio. ");
		}
		if(userDto.getLastName().isEmpty()) {
			errorsFound.append("El apellido no puede estar vacio. ");
		}
		if(userDto.getEmail().isEmpty()) {
			errorsFound.append("El email no puede estar vacio. ");
		}
		if(userDto.getPassword().isEmpty()) {
			errorsFound.append("La contraseña no puede estar vacia.");
		}
		if(errorsFound.length() > 0) {
			throw new BadRequestException(errorsFound.toString());
		}
		Role roleEntity = roleRepo.findByName(userDtoFound.get().getRole().getName());
		User userEntity = UserDtoRequestForUserToEntity(userDto);
		userEntity.setId(userDtoFound.get().getId());
		userEntity.setRole(roleEntity);
		userRepo.save(userEntity);
		return entityToNewDto(userEntity);
	}

	@Override
	public void delete(Long id) throws NotFoundException {
		boolean userExists = userRepo.existsById(id);
		if(!userExists) {
			throw new NotFoundException("The user with id " + id + " does not exist");
		}
		userRepo.deleteById(id);
	}
}