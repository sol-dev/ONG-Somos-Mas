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
import com.team32.ong.security.JWTUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
	@Autowired
	private JWTUtil jwtUtil;

	@Override
	public Map<String,Object> save(UserDTORequest userDTORequest) throws NotFoundException, BadRequestException, IOException {
		Map<String,Object> response = new HashMap<>();
		StringBuffer errorsFound = new StringBuffer();

		if (userRepo.existsByEmail(userDTORequest.getEmail())){
			throw new NotFoundException(ConstantExceptionMessage.MSG_EMAIL_IN_USE);
		}else if (userDTORequest.getEmail().isEmpty()){
			throw new BadRequestException(ConstantExceptionMessage.MSG_EMAIL_BAD_REQUEST);
		}else if (userDTORequest.getFirstName().isEmpty()){
			throw new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST);
		}else if (userDTORequest.getLastName().isEmpty()){
			throw new BadRequestException(ConstantExceptionMessage.MSG_LASTNAME_BAD_REQUEST);
		}else if (userDTORequest.getPassword().isEmpty()){
			throw new BadRequestException(ConstantExceptionMessage.MSG_PASSWORD_BAD_REQUEST);
		}else if(!validateEmail(userDTORequest.getEmail())) {
			errorsFound.append(ConstantExceptionMessage.MSG_EMAIL_BAD_REQUEST);
		}else if(!validateString(userDTORequest.getLastName())) {
			errorsFound.append(ConstantExceptionMessage.MSG_LASTNAME_NOT_NUMBER);
		}else if(!validateString(userDTORequest.getFirstName())) {
			errorsFound.append(ConstantExceptionMessage.MSG_NAME_NOT_NUMBER);
		}else if(!validateString(userDTORequest.getLastName())) {
			errorsFound.append(ConstantExceptionMessage.MSG_LASTNAME_NOT_NUMBER);
		}else if(errorsFound.length() > 0) {
			throw new BadRequestException(errorsFound.toString());
		}
		userDTORequest.setPassword(encoder.encode(userDTORequest.getPassword()));
		Role role = roleRepo.findByName("ROLE_USER");
		User userEntity = dtoToEntity(userDTORequest);
		userEntity.setRole(role);
		User userSave = userRepo.save(userEntity);

		emailService.sendEmail(userSave.getEmail());

        String jwt = jwtUtil.generateToken(userSave);
        
        response.put("userSave", entityToDto(userSave));
        response.put("jwt", jwt);
        
        return response;
    }

    @Override
    public UserDTOResponse getMe(String jwt) throws NotFoundException{

		String emailUser = jwtUtil.extractUsername(jwt.substring(7));

    	User userEntity = userRepo.findByEmail(emailUser);

    	if (userEntity == null){
    		throw new NotFoundException(ConstantExceptionMessage.MSG_EMAIL_NOT_FOUND);
		}
    	return entityToDto(userEntity);

	}
    
    @Override
    public UserDTOResponse findById(Long id) throws NotFoundException{
    	User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("No existe un usuario con ese id"));
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
		userEntity.setPassword(encoder.encode(userDto.getPassword()));
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
		userEntity.setPassword(encoder.encode(userDto.getPassword()));
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
