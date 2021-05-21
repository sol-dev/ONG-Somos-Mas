package com.team32.ong.repository;

import com.team32.ong.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    public User findByLastName(String lastName);
    public boolean existsByEmail(String email);
}
