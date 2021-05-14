package com.team32.ong.service.impl;

import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.dto.NewUserDto;
import com.team32.ong.dto.UserDTORequest;
import com.team32.ong.dto.UserDTOResponse;
import com.team32.ong.dto.UserDtoRequestForAdmin;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.model.Role;
import com.team32.ong.model.User;
import com.team32.ong.repository.RoleRepository;
import com.team32.ong.repository.UserRepository;
import com.team32.ong.service.EmailService;
import com.team32.ong.service.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class UserImplService implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepo;
    
    @Autowired
    private EmailService emailService;

    @Override
    public UserDTOResponse save(UserDTORequest userDTORequest) throws NotFoundException, BadRequestException, IOException {

        if (userRepo.existsByEmail(userDTORequest.getEmail())){
            throw new NotFoundException(ConstantExceptionMessage.MSG_EMAIL_IN_USE);
        }else if (userDTORequest.getEmail() == null){
            throw new BadRequestException(ConstantExceptionMessage.MSG_EMAIL_BAD_REQUEST);
        }else if (userDTORequest.getFirstName() == null){
            throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
        }else if (userDTORequest.getLastName() == null){
            throw new BadRequestException(ConstantExceptionMessage.MSG_LASTNAME_BAD_REQUEST);
        }else if (userDTORequest.getPassword() == null){
            throw new BadRequestException(ConstantExceptionMessage.MSG_PASSWORD_BAD_REQUEST);
        }
        userDTORequest.setPassword(encoder.encode(userDTORequest.getPassword()));

        Role role = roleRepo.findByName("USER");

        User userEntity = dtoToEntity(userDTORequest);

        userEntity.setRole(role);
        User userSave = userRepo.save(userEntity);
        
        emailService.sendEmail(userSave.getEmail());

        return entityToDto(userSave);

    }
    
    @Override
    public UserDTOResponse getOne(Long id) {
    	User user = userRepo.getOne(id);
		return entityToDto(user);
    }
    
    @Override
    public UserDTOResponse findById(Long id) {
    	User user = userRepo.findById(id).orElseThrow(() -> new InvalidDataException("No existe un usuario con ese id"));
    	return entityToDto(user);

    }

    @Override
    public List<UserDTOResponse> getAllUsers(){

        List<User> userList = userRepo.findAll();

        return userList
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email);

        if (user == null){
            throw new UsernameNotFoundException(ConstantExceptionMessage.MSG_EMAIL_NOT_FOUND);
        }

        List<GrantedAuthority> rol = new ArrayList<>();
        rol.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new org.springframework.security.core.userdetails.
                User(user.getEmail(), user.getPassword(), rol);
    }

    
    @Override
    public User dtoToEntity(UserDTORequest userDTORequest){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userDTORequest, User.class);
    }
    
    private NewUserDto entityToNewDto(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, NewUserDto.class);
    }

    @Override
    public UserDTOResponse entityToDto(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserDTOResponse.class);
    }
    
    private User UserDtoRequestForUserToEntity(UserDtoRequestForAdmin userDto) {
    	ModelMapper mapper = new ModelMapper();
        return mapper.map(userDto, User.class);
	}
	
	@Override
	public NewUserDto updateAdminOnly(Long id, UserDtoRequestForAdmin userDto) throws NotFoundException {
		Optional<UserDTOResponse> userDtoFound =  Optional.of(findById(id));
		StringBuffer errorsFound = new StringBuffer();
		
		if(userDto.getFirstName().isEmpty()) {
			errorsFound.append(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
		}
		if(userDto.getLastName().isEmpty()) {
			errorsFound.append(ConstantExceptionMessage.MSG_LASTNAME_BAD_REQUEST);
		}
		if(userDto.getEmail().isEmpty()) {
			errorsFound.append(ConstantExceptionMessage.MSG_EMAIL_BAD_REQUEST);
		}
		if(userDto.getPassword().isEmpty()) {
			errorsFound.append(ConstantExceptionMessage.MSG_PASSWORD_BAD_REQUEST);
		}
		if(errorsFound.length() > 0) {
			throw new BadRequestException(errorsFound.toString());
		}
		User userEntity = UserDtoRequestForUserToEntity(userDto);
		userEntity.setId(userDtoFound.get().getId());
		Role roleEntity = roleRepo.findByName(userDto.getRole().getName());
		userEntity.setRole(roleEntity);
		userRepo.save(userEntity);
		return entityToNewDto(userEntity);
	}
	
	@Override
	public NewUserDto updateForUser(Long id, UserDTORequest userDto) throws NotFoundException {
		Optional<UserDTOResponse> userDtoFound =  Optional.of(findById(id));
		StringBuffer errorsFound = new StringBuffer();
		
		if(userDto.getFirstName().isEmpty()) {
			errorsFound.append(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
		}
		if(userDto.getLastName().isEmpty()) {
			errorsFound.append(ConstantExceptionMessage.MSG_LASTNAME_BAD_REQUEST);
		}
		if(userDto.getEmail().isEmpty()) {
			errorsFound.append(ConstantExceptionMessage.MSG_EMAIL_BAD_REQUEST);
		}
		if(userDto.getPassword().isEmpty()) {
			errorsFound.append(ConstantExceptionMessage.MSG_PASSWORD_BAD_REQUEST);
		}
		if(errorsFound.length() > 0) {
			throw new BadRequestException(errorsFound.toString());
		}
		Role roleEntity = roleRepo.findByName("ROLE_USER");
		User userEntity = dtoToEntity(userDto);
		userEntity.setId(userDtoFound.get().getId());
		userEntity.setRole(roleEntity);
		userRepo.save(userEntity);
		return entityToNewDto(userEntity);
	}
	@Override
	public String delete(Long id) throws NotFoundException {
		boolean userExists = userRepo.existsById(id);
		if(!userExists) {
			throw new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND + id);
		}
		userRepo.deleteById(id);
		return ConstantExceptionMessage.MSG_DELETE_OK + id;
	}
}
