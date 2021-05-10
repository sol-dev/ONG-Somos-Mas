package com.team32.ong.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.team32.ong.constant.ConstantMessage;
import com.team32.ong.dto.NewUserDto;
import com.team32.ong.dto.RoleDto;
import com.team32.ong.dto.UserDto;
import com.team32.ong.dto.UserDtoRequestForUser;
import com.team32.ong.enums.RoleName;
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
            throw new NotFoundException(ConstantMessage.MSG_NOT_FOUND + id);
        }
		return entityToDto(userFound.get());
	}
	
	@Override
	public NewUserDto updateAdminOnly(Long id, UserDto userDto) throws NotFoundException {
		Optional<UserDto> userDtoFound =  Optional.of(findById(id));
		StringBuffer errorsFound = new StringBuffer();
		
		if(userDto.getFirstName().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_NAME_BAD_REQUEST);
		}
		if(userDto.getLastName().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_LASTNAME_BAD_REQUEST);
		}
		if(userDto.getEmail().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_EMAIL_BAD_REQUEST);
		}
		if(userDto.getPassword().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_PASSWORD_BAD_REQUEST);
		}
		if(errorsFound.length() > 0) {
			throw new BadRequestException(errorsFound.toString());
		}
		User userEntity = dtoToEntity(userDto);
		userEntity.setId(userDtoFound.get().getId());
		if(userDto.getRole().getName().equals("ROLE_ADMIN")) {
			Role roleEntity = roleRepo.findByName(RoleName.ROLE_ADMIN);
			userEntity.setRole(roleEntity);
		}else {
			Role roleEntity = roleRepo.findByName(RoleName.ROLE_USER);
			userEntity.setRole(roleEntity);
		}
		userRepo.save(userEntity);
		return entityToNewDto(userEntity);
	}
	
	@Override
	public NewUserDto updateForUser(Long id, UserDtoRequestForUser userDto) throws NotFoundException {
		Optional<UserDto> userDtoFound =  Optional.of(findById(id));
		StringBuffer errorsFound = new StringBuffer();
		
		if(userDto.getFirstName().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_NAME_BAD_REQUEST);
		}
		if(userDto.getLastName().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_LASTNAME_BAD_REQUEST);
		}
		if(userDto.getEmail().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_EMAIL_BAD_REQUEST);
		}
		if(userDto.getPassword().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_PASSWORD_BAD_REQUEST);
		}
		if(errorsFound.length() > 0) {
			throw new BadRequestException(errorsFound.toString());
		}		
		Role roleEntity = roleRepo.findByName(RoleName.ROLE_USER);
		User userEntity = UserDtoRequestForUserToEntity(userDto);
		userEntity.setId(userDtoFound.get().getId());
		userEntity.setRole(roleEntity);
		userRepo.save(userEntity);
		return entityToNewDto(userEntity);
	}

	@Override
	public String delete(Long id) throws NotFoundException {
		boolean userExists = userRepo.existsById(id);
		if(!userExists) {
			throw new NotFoundException(ConstantMessage.MSG_NOT_FOUND + id);
		}
		userRepo.deleteById(id);
		return ConstantMessage.MSG_DELETE_OK + id;
	}
}