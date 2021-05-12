package com.team32.ong.service.impl;

import com.team32.ong.constant.ConstantMessage;
import com.team32.ong.dto.UserDTORequest;
import com.team32.ong.dto.UserDTOResponse;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.exception.custom.InvalidDataException;
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
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public UserDTOResponse findById(Long id) {
    	User user = userRepo.findById(id).orElseThrow(() -> new InvalidDataException("No existe un usuario con ese id"));
    	return entityToDto(user);

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

    @Override
    public UserDTOResponse entityToDto(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserDTOResponse.class);
    }
}
