package com.team32.ong.repository;

import com.team32.ong.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import com.team32.ong.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
    public Role findByName(RoleName name);
    public boolean existsByName(String name);
}
