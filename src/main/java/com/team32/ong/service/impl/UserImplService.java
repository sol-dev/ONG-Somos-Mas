package com.team32.ong.service.impl;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);

        List<GrantedAuthority> rol = new ArrayList<>();
        rol.add(new SimpleGrantedAuthority(user.getRole().getName().name()));

        return new org.springframework.security.core.userdetails.
                User(user.getEmail(), user.getPassword(), rol);
    }

	@Override
	public Boolean rolValidation(UserRequest userRequest) {
		Optional<User> userOpt = userRepo.findById(userRequest.getId());
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			if (user.getRole().getId() == 1) {
				return true;
			}
		}
		return false;
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
