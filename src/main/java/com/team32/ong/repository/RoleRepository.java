package com.team32.ong.repository;

import com.team32.ong.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
    public boolean existsByName(String name);
}
