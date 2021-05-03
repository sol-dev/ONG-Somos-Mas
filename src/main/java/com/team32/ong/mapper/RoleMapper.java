package com.team32.ong.mapper;

import com.team32.ong.dto.RoleDto;
import com.team32.ong.model.Role;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleMapper {

    public Role toRole(RoleDto roleDto){
        ModelMapper maper = new ModelMapper();
        return maper.map(RoleDto, Role.class );
    }

    public RoleDto toRoleDto(Role role){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(role, RoleDto.class);
    }
}
