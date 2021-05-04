package com.team32.ong.service.impl;

import com.team32.ong.dto.RoleDto;
import com.team32.ong.dto.UserRequest;
import com.team32.ong.dto.UserResponse;
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

@Service
public class UserImplService implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public UserResponse save(UserRequest user) {

        user.setPassword(encoder.encode(user.getPassword()));

        Role role = roleRepo.findByName("USER");
        user.setRole(roleEntityToDto(role));

        User userEntity = userRepo.save(dtoToEntity(user));

        return entityToDto(userEntity);
    }

    @Override
    public UserResponse login(UserRequest user) {

        User userEntity = userRepo.findByEmail(user.getEmail());

        

        loadUserByUsername(user.getEmail());

        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email);

        List<GrantedAuthority> rol = new ArrayList<>();
        rol.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), rol);
    }


    private User dtoToEntity(UserRequest userRequest){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userRequest, User.class);
    }

    private UserResponse entityToDto(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserResponse.class);
    }

    private RoleDto roleEntityToDto(Role role){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(role, RoleDto.class);
    }


}
