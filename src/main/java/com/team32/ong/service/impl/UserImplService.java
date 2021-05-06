package com.team32.ong.service.impl;

import com.team32.ong.dto.UserRequest;
import com.team32.ong.dto.UserResponse;
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

@Service
public class UserImplService implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public UserResponse save(UserRequest userRequest) throws NotFoundException, BadRequestException {

        if (userRepo.existsByEmail(userRequest.getEmail())){
            throw new NotFoundException("Este email ya esta registrado");
        }else if (userRequest.getEmail() == null){
            throw new BadRequestException("Se necesita definir un Email");
        }else if (userRequest.getFirstName() == null){
            throw new BadRequestException("Se necesita definir un Nombre");
        }else if (userRequest.getLastName() == null){
            throw new BadRequestException("Se necesita definir un Apellido");
        }else if (userRequest.getPassword() == null){
            throw new BadRequestException("Se necesita definir una Contraseña");
        }
        userRequest.setPassword(encoder.encode(userRequest.getPassword()));

        Role role = roleRepo.findByName("USER");

        User userEntity = dtoToEntity(userRequest);
        userEntity.setRole(role);
        User userSave = userRepo.save(userEntity);

        return entityToDto(userSave);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if (!userRepo.existsByEmail(email)){
            throw new UsernameNotFoundException("Este mail no es un usuario registrado");
        }
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

    private UserResponse entityToDto(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserResponse.class);
    }


}
