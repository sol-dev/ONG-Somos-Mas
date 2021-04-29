package com.team32.ong.mapper;


import com.team32.ong.dto.UserDto;
import com.team32.ong.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserDto userDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userDto, User.class);
    }

    public UserDto toUserDto(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserDto.class);
    }
}
