package com.team32.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team32.ong.model.Comment;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    public boolean existsById(Long id);
    public Optional<Comment> findById(Long id);

}
