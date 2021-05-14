package com.team32.ong.service.impl;

import com.team32.ong.constant.ConstantMessage;
import com.team32.ong.dto.NewUserDto;
import com.team32.ong.dto.UserDTORequest;
import com.team32.ong.dto.UserDTOResponse;
import com.team32.ong.dto.UserDtoRequestForAdmin;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.model.Role;
import com.team32.ong.model.User;
import com.team32.ong.repository.RoleRepository;
import com.team32.ong.repository.UserRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserImplService implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepo;

    @Override

    public UserDTOResponse save(UserDTORequest userDTORequest) throws NotFoundException, BadRequestException {

        if (userRepo.existsByEmail(userDTORequest.getEmail())){
            throw new NotFoundException(ConstantMessage.MSG_EMAIL_IN_USE);
        }else if (userDTORequest.getEmail() == null){
            throw new BadRequestException(ConstantMessage.MSG_EMAIL_BAD_REQUEST);
        }else if (userDTORequest.getFirstName() == null){
            throw new BadRequestException(ConstantMessage.MSG_NAME_BAD_REQUEST);
        }else if (userDTORequest.getLastName() == null){
            throw new BadRequestException(ConstantMessage.MSG_LASTNAME_BAD_REQUEST);
        }else if (userDTORequest.getPassword() == null){
            throw new BadRequestException(ConstantMessage.MSG_PASSWORD_BAD_REQUEST);
        }
        userDTORequest.setPassword(encoder.encode(userDTORequest.getPassword()));

        Role role = roleRepo.findByName("ROLE_USER");

        User userEntity = dtoToEntity(userDTORequest);

        userEntity.setRole(role);
        User userSave = userRepo.save(userEntity);

        return entityToDto(userSave);

    }
    
    @Override
    public UserDTOResponse getOne(Long id) {
    	User user = userRepo.getOne(id);
		return entityToDto(user);
    }
    
    @Override
    public UserDTOResponse findById(Long id) throws NotFoundException {
    	User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException(ConstantMessage.MSG_NOT_FOUND + id));
    	return entityToDto(user);

    }
    
    public UserDtoRequestForAdmin findByIdRequest(Long id) {
    	Optional<User> user = userRepo.findById(id);
    	return entityToUserDtoRequestForUser(user.get());

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
            throw new UsernameNotFoundException(ConstantMessage.MSG_EMAIL_NOT_FOUND);
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
    
    private UserDtoRequestForAdmin entityToUserDtoRequestForUser(User user) {
    	ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserDtoRequestForAdmin.class);
	}
	
	@Override
	public NewUserDto updateAdminOnly(Long id, UserDtoRequestForAdmin userDto) throws NotFoundException {
		Optional<UserDTOResponse> userDtoFound =  Optional.of(findById(id));
		Optional<UserDtoRequestForAdmin> userDtoFoundRequest =  Optional.of(findByIdRequest(id));
		StringBuffer errorsFound = new StringBuffer();
		User userEntity = UserDtoRequestForUserToEntity(userDto);
		User userEntityForGetPassRol = UserDtoRequestForUserToEntity(userDtoFoundRequest.get());		
		if(userDto.getFirstName() == null) {
			userEntity.setFirstName(userDtoFound.get().getFirstName());
		}
		if(userDto.getLastName() == null) {
			userEntity.setLastName(userDtoFound.get().getLastName());
		}
		if(userDto.getEmail() == null) {
			userEntity.setEmail(userDtoFound.get().getEmail());
		}
		if(userDto.getPassword() == null) {
			userEntity.setPassword(userEntityForGetPassRol.getPassword());
		}
		if(userDto.getRole() == null) {
			userEntity.setRole(userEntityForGetPassRol.getRole());
		}
		if(userEntity.getFirstName().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_NAME_BAD_REQUEST);
		}
		if(userEntity.getLastName().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_LASTNAME_BAD_REQUEST);
		}
		if(userEntity.getEmail().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_EMAIL_BAD_REQUEST);
		}
		if(userEntity.getPassword().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_PASSWORD_BAD_REQUEST);
		}
		if(!validateEmail(userEntity.getEmail())) {
			errorsFound.append(ConstantMessage.MSG_EMAIL_BAD_REQUEST);
		}
		if(!validateString(userEntity.getFirstName())) {
			errorsFound.append(ConstantMessage.MSG_NAME_NOT_NUMBER);
		}
		if(!validateString(userEntity.getLastName())) {
			errorsFound.append(ConstantMessage.MSG_LASTNAME_NOT_NUMBER);
		}
		if(userEntity.getRole().getName().equalsIgnoreCase("ROLE_ADMIN")) {
			Role roleEntity = roleRepo.findByName("ROLE_ADMIN");
			userEntity.setRole(roleEntity);
		}else if(userEntity.getRole().getName().equalsIgnoreCase("ROLE_USER")){
			Role roleEntity = roleRepo.findByName("ROLE_USER");
			userEntity.setRole(roleEntity);
		}else {
			errorsFound.append(ConstantMessage.MSG_ROL_BAD_REQUEST);
		}
		if(errorsFound.length() > 0) {
			throw new BadRequestException(errorsFound.toString());
		}
		userEntity.setId(userDtoFound.get().getId());
		userRepo.save(userEntity);
		return entityToNewDto(userEntity);
	}
	
	@Override
	public NewUserDto updateForUser(Long id, UserDTORequest userDto) throws NotFoundException {
		Optional<UserDTOResponse> userDtoFound =  Optional.of(findById(id));
		Optional<UserDtoRequestForAdmin> userDtoFoundRequest =  Optional.of(findByIdRequest(id));
		User userEntity = dtoToEntity(userDto);
		User userEntityForGetPassRol = UserDtoRequestForUserToEntity(userDtoFoundRequest.get());
		StringBuffer errorsFound = new StringBuffer();
		if(userDto.getFirstName() == null) {
			userEntity.setFirstName(userDtoFound.get().getFirstName());
		}
		if(userDto.getLastName() == null) {
			userEntity.setLastName(userDtoFound.get().getLastName());
		}
		if(userDto.getEmail() == null) {
			userEntity.setEmail(userDtoFound.get().getEmail());
		}
		if(userDto.getPassword() == null) {
			userEntity.setPassword(userEntityForGetPassRol.getPassword());
		}
		if(userEntity.getFirstName().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_NAME_BAD_REQUEST);
		}
		if(userEntity.getLastName().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_LASTNAME_BAD_REQUEST);
		}
		if(userEntity.getEmail().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_EMAIL_BAD_REQUEST);
		}
		if(userEntity.getPassword().isEmpty()) {
			errorsFound.append(ConstantMessage.MSG_PASSWORD_BAD_REQUEST);
		}
		if(!validateEmail(userEntity.getEmail())) {
			errorsFound.append(ConstantMessage.MSG_EMAIL_BAD_REQUEST);
		}
		if(!validateString(userEntity.getFirstName())) {
			errorsFound.append(ConstantMessage.MSG_NAME_NOT_NUMBER);
		}
		if(!validateString(userEntity.getLastName())) {
			errorsFound.append(ConstantMessage.MSG_LASTNAME_NOT_NUMBER);
		}
		if(errorsFound.length() > 0) {
			throw new BadRequestException(errorsFound.toString());
		}
		Role roleEntity = roleRepo.findByName("ROLE_USER");
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
	
	private boolean validateEmail(String email) {
    	Pattern regex = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
    	Matcher m = regex.matcher(email);
    	return m.find() ? true : false;
    }
	
	private boolean validateString(String validation) {
		boolean flag = true;		
    	for(int i=0;i < validation.length();i++) {
    		if(Character.isDigit(validation.charAt(i))) {
    			flag=false;
    			break;
    		}
    	}
    	return flag;
    }
}
