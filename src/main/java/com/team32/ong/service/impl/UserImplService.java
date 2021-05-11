package com.team32.ong.service.impl;

import com.team32.ong.dto.UserRequest;
import com.team32.ong.dto.UserResponse;
import com.team32.ong.exception.custom.InvalidDataException;
import com.team32.ong.model.Role;
import com.team32.ong.model.User;
import com.team32.ong.repository.RoleRepository;
import com.team32.ong.repository.UserRepository;
import com.team32.ong.service.UserService;
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
    public UserResponse save(UserRequest userRequest) {

        userRequest.setPassword(encoder.encode(userRequest.getPassword()));
        
        Role role = roleRepo.findByName("USER");

        User userEntity = dtoToEntity(userRequest);
        userEntity.setRole(role);
        User userSave = userRepo.save(userEntity);

        return entityToDto(userSave);
    }
    
    @Override
    public UserResponse getOne(Long id) {
    	User user = userRepo.getOne(id);
		return entityToDto(user);
    }
    
    @Override
    public UserResponse findById(Long id) {
    	User user = userRepo.findById(id).orElseThrow(() -> new InvalidDataException("No existe un usuario con ese id"));
    	return entityToDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email);

        List<GrantedAuthority> rol = new ArrayList<>();
        rol.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new org.springframework.security.core.userdetails.
                User(user.getEmail(), user.getPassword(), rol);
    }

    private User dtoToEntity(UserRequest userRequest){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userRequest, User.class);
    }

    public UserResponse entityToDto(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserResponse.class);
    }
    
    public User dtoToEntity(UserResponse userDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userDto, User.class);
    }

}
